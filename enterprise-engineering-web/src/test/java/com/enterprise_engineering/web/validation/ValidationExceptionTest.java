package com.enterprise_engineering.web.validation;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationExceptionTest {

    @Test
    void shouldCreateValidationException() {
        var validationError = mock(ValidationError.class);
        var actual = new ValidationException(validationError);
        assertThat(actual.getValidationError(), is(validationError));
    }

    @Test
    void shouldGetValidationMessage() {
        Map<String, String> validationErrors = ImmutableMap.of("name1", "field1", "name2", "field2");
        var validationError = mock(ValidationError.class);
        when(validationError.getErrors()).thenReturn(validationErrors);
        var actual = new ValidationException(validationError);
        assertThat(actual.getMessage(), is("name1:field1,name2:field2"));
    }

}