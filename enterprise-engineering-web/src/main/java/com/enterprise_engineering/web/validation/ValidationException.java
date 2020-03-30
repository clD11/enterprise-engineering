package com.enterprise_engineering.web.validation;

import com.google.common.base.Joiner;

public class ValidationException extends RuntimeException {
    private final ValidationError validationError;

    public ValidationException(ValidationError validationError) {
        this.validationError = validationError;
    }

    public ValidationError getValidationError() {
        return validationError;
    }

    @Override
    public String getMessage() {
        return Joiner.on(',').withKeyValueSeparator(':').join(validationError.getErrors());
    }

}
