package com.enterprise_engineering.web.validation;

import com.enterprise_engineering.test_supprt.random.RandomUtil;
import com.enterprise_engineering.test_supprt.serialization.TestSupportSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ValidationErrorTest {

    private final ValidationError validationError = new ValidationError();

    @Test
    void shouldHaveErrorsKeyPairNameMessage() {
        var name = RandomUtil.randomString();
        var message = RandomUtil.randomString();
        validationError.addError(name, message);
        assertThat(validationError.getErrors().get(name), is(message));
    }

    @Test
    void shouldHaveErrors() {
        var name = RandomUtil.randomString();
        var message = RandomUtil.randomString();
        validationError.addError(name, message);
        assertThat(validationError.hasErrors(), is(true));
    }

    @Test
    void shouldNotHaveErrors() {
        assertThat(validationError.hasErrors(), is(false));
    }

    @Test
    void shouldSerializeValidationErrors() throws JsonProcessingException {
        var name = RandomUtil.randomString();
        var message = RandomUtil.randomString();
        validationError.addError(name, message);
        var actual = TestSupportSerializer.asJsonString(validationError);
        assertValidationErrors(name, message, actual);
    }

    private void assertValidationErrors(String name, String message, String actual) {
        assertThat(actual, is("{\"errors\":{\"" + name + "\":\"" + message + "\"}}"));
    }

}