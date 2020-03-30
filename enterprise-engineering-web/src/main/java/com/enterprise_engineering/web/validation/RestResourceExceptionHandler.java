package com.enterprise_engineering.web.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestResourceExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestResourceExceptionHandler.class);

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ResponseEntity<ValidationError> handleValidationException(ValidationException validationException) {
        logger.error("Validation Error {}", validationException.getValidationError());
        return new ResponseEntity<>(validationException.getValidationError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
        logger.error("Internal Server Error: {}", runtimeException.getMessage());
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
