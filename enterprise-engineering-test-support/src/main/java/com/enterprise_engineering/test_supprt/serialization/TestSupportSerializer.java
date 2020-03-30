package com.enterprise_engineering.test_supprt.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestSupportSerializer {

    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String asJsonString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static String asXmlString(Object obj) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(obj);
    }
}
