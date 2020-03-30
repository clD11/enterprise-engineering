package com.enterprise_engineering.web.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidationError {

    @JsonProperty(value = "errors")
    private final Map<String, String> errors = new HashMap<>();

    public void addError(String name, String message) {
        errors.put(name, message);
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

}
