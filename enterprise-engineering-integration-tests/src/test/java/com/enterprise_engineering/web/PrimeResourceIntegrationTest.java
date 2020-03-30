package com.enterprise_engineering.web;

import com.enterprise_engineering.web.resource.PrimeResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static com.enterprise_engineering.test_supprt.random.RandomUtil.*;
import static com.enterprise_engineering.test_supprt.serialization.TestSupportSerializer.*;
import static com.enterprise_engineering.web.resource.PrimeResponseDto.PrimeResponseDtoBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrimeResourceIntegrationTest {

    private static final List<String> ALGORITHMS = Collections.unmodifiableList(List.of("naive", "eratosthenes", "parallel"));

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldGetPrimeNumbers() throws JsonProcessingException {
        var primeResponseDto = buildPrimeResponseDto();
        String url = String.format("http://localhost:%d/api/v1/primes/10", port);
        var expected = asJsonString(primeResponseDto);
        var actual = testRestTemplate.getForEntity(url, String.class);
        assertSuccess(actual, expected, APPLICATION_JSON);
    }

    @Test
    void shouldGetPrimeNumbersWithOptionalParameter() throws JsonProcessingException {
        var primeResponseDto = buildPrimeResponseDto();
        String url = String.format("http://localhost:%d/api/v1/primes/10?algorithm=%s", port, nextRandomString(ALGORITHMS));
        var expected = asJsonString(primeResponseDto);
        var actual = testRestTemplate.getForEntity(url, String.class);
        assertSuccess(actual, expected, APPLICATION_JSON);
    }

    @Test
    void shouldGetPrimeNumbersHeaderJson() throws JsonProcessingException {
        var primeResponseDto = buildPrimeResponseDto();
        var request = createHttpRequest(APPLICATION_JSON);
        var url = String.format("http://localhost:%d/api/v1/primes/%d", port, primeResponseDto.getInitial());
        var expected = asJsonString(primeResponseDto);
        var actual = testRestTemplate.exchange(url, HttpMethod.GET, request, String.class);
        assertSuccess(actual, expected, APPLICATION_JSON);
    }

    @Test
    void shouldGetPrimeNumbersHeaderXml() throws JsonProcessingException {
        var primeResponseDto = buildPrimeResponseDto();
        var request = createHttpRequest(APPLICATION_XML);
        var url = String.format("http://localhost:%d/api/v1/primes/%d", port, primeResponseDto.getInitial());
        var expected = asXmlString(primeResponseDto);
        var actual = testRestTemplate.exchange(url, HttpMethod.GET, request, String.class);
        assertSuccess(actual, expected, APPLICATION_XML);
    }

    @Test
    void shouldGetPrimeNumbersParameterJson() throws JsonProcessingException {
        var primeResponseDto = buildPrimeResponseDto();
        var url = String.format("http://localhost:%d/api/v1/primes/%d?mediaType=json", port, primeResponseDto.getInitial());
        var expected = asJsonString(primeResponseDto);
        var actual = testRestTemplate.getForEntity(url, String.class);
        assertSuccess(actual, expected, APPLICATION_JSON);
    }

    @Test
    void shouldGetPrimeNumbersParameterXml() throws JsonProcessingException {
        var primeResponseDto = buildPrimeResponseDto();
        var url = String.format("http://localhost:%d/api/v1/primes/%d?mediaType=xml", port, primeResponseDto.getInitial());
        var expected = asXmlString(primeResponseDto);
        var actual = testRestTemplate.getForEntity(url, String.class);
        assertSuccess(actual, expected, APPLICATION_XML);
    }

    private PrimeResponseDto buildPrimeResponseDto() {
        return newPrimeResponseDtoBuilder()
                .withInitial(10)
                .withPrimes(List.of(2, 3, 5, 7))
                .build();
    }

    private HttpEntity<Object> createHttpRequest(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(mediaType));
        return new HttpEntity<>(headers);
    }

    private void assertSuccess(ResponseEntity<String> actual, String expected, MediaType mediaType) {
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getHeaders().getContentType(), is(mediaType));
        assertThat(actual.getBody(), is(expected));
    }
}
