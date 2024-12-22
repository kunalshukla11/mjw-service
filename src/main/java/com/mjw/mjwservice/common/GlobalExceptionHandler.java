package com.mjw.mjwservice.common;

import com.mjw.mjwservice.exception.ValidationException;
import com.mjw.mjwservice.validation.model.ErrorResponse;
import com.mjw.mjwservice.validation.model.ValidationResponse;
import com.mjw.mjwservice.validation.util.ValidatorUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Optional;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleValidationException(final ValidationException exception) {
        return ResponseEntity.unprocessableEntity()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .validationResponse(exception.getValidationResponse())
                        .build());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(final WebExchangeBindException exception) {
        return Optional.of(exception)
                .map(WebExchangeBindException::getBindingResult)
                .map(bindingResult -> ValidatorUtil.mapFieldErrorsToFieldViolations(bindingResult.getFieldErrors()))
                .filter(CollectionUtils::isNotEmpty)
                .map(fieldViolations -> ValidationResponse.builder().fieldViolations(fieldViolations).build())
                .map(validationResponse -> ErrorResponse.builder()
                        .message("Request Failed in Validation")
                        .validationResponse(validationResponse)
                        .build())
                .map(errorResponse -> ResponseEntity.badRequest().body(errorResponse))
                .orElse(ResponseEntity.badRequest()
                        .body(ErrorResponse.builder()
                                .message("Request Failed in Validation without error details")
                                .build()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException exception) {
        log.error("Exception Occurred:", exception);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                        .detailedMessage(Optional.ofNullable(exception.getCause())
                                .map(Throwable::getMessage)
                                .orElse(null))
                        .message(exception.getMessage())
                        .build());
    }

}
