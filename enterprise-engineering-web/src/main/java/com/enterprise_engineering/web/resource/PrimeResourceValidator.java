package com.enterprise_engineering.web.resource;

import com.enterprise_engineering.web.validation.ValidationError;
import com.enterprise_engineering.web.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PrimeResourceValidator {

    public void validate(int initial) {
        ValidationError validationError = new ValidationError();

        if (initial < 0) {
            validationError.addError("initial", "Initial value cannot be negative");
        }

        if (validationError.hasErrors())  {
            throw new ValidationException(validationError);
        }
    }
}
