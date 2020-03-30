package com.enterprise_engineering.web.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestResourceExceptionHandlerTest {

    private final RestResourceExceptionHandler restControllerExceptionHandler = new RestResourceExceptionHandler();

    @Test
    void shouldHandleRuntimeException() {
        var runtimeException = mock(RuntimeException.class);
        var actual = restControllerExceptionHandler.handleRuntimeException(runtimeException);
        assertThat(actual.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(actual.getBody(), is("Internal Server Error"));
    }

    @Test
    void shouldHandleValidationException() {
        var validationErrors = mock(ValidationError.class);
        var validationException = mock(ValidationException.class);
        when(validationException.getValidationError()).thenReturn(validationErrors);

        var actual = restControllerExceptionHandler.handleValidationException(validationException);

        assertThat(actual.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(actual.getBody(), is(validationErrors));
    }

}