package com.enterprise_engineering.web.resource;

import com.enterprise_engineering.test_supprt.random.RandomUtil;
import com.enterprise_engineering.web.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static com.enterprise_engineering.test_supprt.random.RandomUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrimeResourceValidatorTest {

    private final PrimeResourceValidator primeResourceValidator = new PrimeResourceValidator();

    @Test
    void shouldThrowValidationExceptionWhenNegativeValue() {
        int initial = nextNegativeInteger();
        ValidationException actual = assertThrows(ValidationException.class, () ->
                primeResourceValidator.validate(initial));
        assertThat(actual.getValidationError().getErrors().get("initial"), is("Initial value cannot be negative"));
    }

    @Test
    void shouldNotContainValidationErrorForPositiveValue() {
        int initial = nextInteger();
        assertDoesNotThrow(() -> primeResourceValidator.validate(initial));
    }

}