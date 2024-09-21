#!/bin/bash

# Exit script on error
set -e

# Function to prompt for input if not provided
prompt_input() {
  local var_name="$1"
  local prompt_message="$2"
  local default_value="$3"

  if [ -z "${!var_name}" ]; then
    read -p "$prompt_message [${default_value}]: " input
    export "$var_name"="${input:-$default_value}"
  fi
}

# Argument parsing and interactive inputs
while getopts "t:i:k:d:" opt; do
  case "$opt" in
    t) TAG="$OPTARG" ;;
    i) OCI_IP="$OPTARG" ;;
    k) PRIVATE_KEY="$OPTARG" ;;
    d) DEST_DIR="$OPTARG" ;;
    *) echo "Usage: $0 -t <tag> -i <oci_ip> -k <path_to_private_key> -d <destination_directory>"; exit 1 ;;
  esac
done

# Prompt for missing arguments
prompt_input TAG "Enter the Docker image tag (e.g., 1.0.0)" "latest"
prompt_input OCI_IP "Enter the OCI instance IP" "192.168.0.1"
prompt_input PRIVATE_KEY "Enter the path to the private key" "~/.ssh/id_rsa"
prompt_input DEST_DIR "Enter the destination directory on OCI instance" "/home/ubuntu/mjw_service"

# Derived variables
SERVICE_DIR="$(pwd)"
APP_DIR="$(realpath "$SCRIPT_DIR../mjw-app")"
echo "APP_DIR: $APP_DIR"
SERVICE_IMAGE="mjw_service:${TAG}"
APP_IMAGE="mjw_app:${TAG}"

# Build Docker images
echo "Building Docker images..."
docker build -t "$SERVICE_IMAGE" "$SERVICE_DIR"
docker build -t "$APP_IMAGE" "$APP_DIR"

# Save Docker images to tar files
echo "Saving Docker images to tar files..."
docker save -o "${SERVICE_DIR}/mjw_service.tar" "$SERVICE_IMAGE"
docker save -o "${SERVICE_DIR}/mjw_app.tar" "$APP_IMAGE"

# Transfer tar files to OCI instance
echo "Transferring images to OCI instance..."
scp -i "$PRIVATE_KEY" "${SERVICE_DIR}/mjw_service.tar" "ubuntu@${OCI_IP}:${DEST_DIR}/"
scp -i "$PRIVATE_KEY" "${SERVICE_DIR}/mjw_app.tar" "ubuntu@${OCI_IP}:${DEST_DIR}/"

# Load images and run docker-compose on OCI instance
echo "Running Docker Compose on OCI instance..."
ssh -i "$PRIVATE_KEY" "ubuntu@${OCI_IP}" << EOF
  set -e
  cd "${DEST_DIR}"
  echo "Loading Docker images..."
  docker load -i mjw_service.tar
  docker load -i mjw_app.tar

  echo "Starting services with Docker Compose..."
  docker-compose -f docker-compose-oci-apps.yml up -d
EOF

echo "Deployment completed successfully!"
