package com.enterprise_engineering.web.resource;

import com.enterprise_engineering.prime.generator.PrimeGenerator;
import com.enterprise_engineering.prime.generator.PrimeGeneratorStrategySelector;
import com.enterprise_engineering.web.validation.ValidationError;
import com.enterprise_engineering.web.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.enterprise_engineering.test_supprt.random.RandomUtil.*;
import static com.enterprise_engineering.test_supprt.random.RandomUtil.nextInteger;
import static com.enterprise_engineering.test_supprt.random.RandomUtil.randomString;
import static com.enterprise_engineering.test_supprt.serialization.TestSupportSerializer.asJsonString;
import static com.enterprise_engineering.test_supprt.serialization.TestSupportSerializer.asXmlString;
import static com.enterprise_engineering.web.resource.PrimeResponseDto.PrimeResponseDtoBuilder.newPrimeResponseDtoBuilder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PrimeResource.class)
class PrimeResourceTest {

    private static final List<String> ALGORITHMS = Collections.unmodifiableList(List.of("naive", "eratosthenes", "parallel"));

    @MockBean
    private PrimeResourceValidator primeResourceValidator;

    @MockBean
    private PrimeGeneratorStrategySelector primeGeneratorStrategySelector;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnJsonResultDefaultAlgorithm() throws Exception {
        var algorithm = "eratosthenes";
        var primeResponseDto = mockRequest(algorithm);
        var expected = asJsonString(primeResponseDto);
        mockMvc.perform(get("/api/v1/primes/" + primeResponseDto.getInitial())
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldReturnXmlResultDefaultAlgorithm() throws Exception {
        var algorithm = "eratosthenes";
        var primeResponseDto = mockRequest(algorithm);
        var expected = asXmlString(primeResponseDto);
        mockMvc.perform(get("/api/v1/primes/" + primeResponseDto.getInitial())
                .accept(APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldReturnJsonWithOptionalParameter() throws Exception {
        var algorithm = nextRandomString(ALGORITHMS);
        var primeResponseDto = mockRequest(algorithm);
        var expected = asJsonString(primeResponseDto);
        mockMvc.perform(get(String.format("/api/v1/primes/%d/?algorithm=%s", primeResponseDto.getInitial(), algorithm))
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldReturnXmlWithOptionalParameter() throws Exception {
        var algorithm = nextRandomString(ALGORITHMS);
        var primeResponseDto = mockRequest(algorithm);
        var expected = asXmlString(primeResponseDto);
        mockMvc.perform(get(String.format("/api/v1/primes/%d/?algorithm=%s", primeResponseDto.getInitial(), algorithm))
                .accept(APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldThrowValidationExceptionWhenNegativeNumber() throws Exception {
        var validationErrors = new ValidationError();
        validationErrors.addError(randomString(), randomString());
        int initial = -1;
        doThrow(new ValidationException(validationErrors)).when(primeResourceValidator).validate(initial);

        mockMvc.perform(get("/api/v1/primes/" + initial)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(asJsonString(validationErrors))));
    }

    private PrimeResponseDto mockRequest(String algorithm) {
        PrimeResponseDto primeResponseDto = newPrimeResponseDtoBuilder()
                .withInitial(nextInteger())
                .withPrimes(List.of(nextInteger(), nextInteger()))
                .build();

        var primeGenerator = mock(PrimeGenerator.class);
        when(primeGenerator.generatePrimes(primeResponseDto.getInitial())).thenReturn(primeResponseDto.getPrimes());

        when(primeGeneratorStrategySelector.getPrimeGenerator(algorithm)).thenReturn(primeGenerator);
        doNothing().when(primeResourceValidator).validate(primeResponseDto.getInitial());

        return primeResponseDto;
    }
}