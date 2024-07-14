package com.mjw.mjwservice.common;

import com.mjw.mjwservice.exception.ValidationException;
import com.mjw.mjwservice.validation.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleValidationException(final ValidationException e) {
        return ResponseEntity.unprocessableEntity()
                .body(ErrorResponse.builder().message(e.getMessage()).violations(e.getViolations()).build());
    }

}
