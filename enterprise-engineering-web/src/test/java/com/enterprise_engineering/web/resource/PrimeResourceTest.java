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

import java.util.List;

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

    @MockBean
    private PrimeResourceValidator primeResourceValidator;

    @MockBean
    private PrimeGeneratorStrategySelector primeGeneratorStrategySelector;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnJsonPrimeNumbersNoAlgorithm() throws Exception {
        var algorithm = "eratosthenes";
        int initial = nextInteger();

        var primes = List.of(nextInteger(), nextInteger());
        PrimeResponseDto primeResponseDto = newPrimeResponseDtoBuilder()
                .withInitial(initial)
                .withPrimes(primes)
                .build();

        var primeGenerator = mock(PrimeGenerator.class);
        when(primeGenerator.generatePrimes(initial)).thenReturn(primeResponseDto.getPrimes());

        when(primeGeneratorStrategySelector.getPrimeGenerator(algorithm)).thenReturn(primeGenerator);
        doNothing().when(primeResourceValidator).validate(initial);

        var expected = asJsonString(primeResponseDto);

        mockMvc.perform(get("/api/v1/primes/" + initial)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldReturnXmlPrimeNumbersNoAlgorithm() throws Exception {
        var algorithm = "eratosthenes";
        int initial = nextInteger();

        var primes = List.of(nextInteger(), nextInteger());
        PrimeResponseDto primeResponseDto = newPrimeResponseDtoBuilder()
                .withInitial(initial)
                .withPrimes(primes)
                .build();

        var primeGenerator = mock(PrimeGenerator.class);
        when(primeGenerator.generatePrimes(initial)).thenReturn(primeResponseDto.getPrimes());

        when(primeGeneratorStrategySelector.getPrimeGenerator(algorithm)).thenReturn(primeGenerator);
        doNothing().when(primeResourceValidator).validate(initial);

        var expected = asXmlString(primeResponseDto);

        mockMvc.perform(get("/api/v1/primes/" + initial)
                .accept(APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldReturnXmlPrimeNumbersNaive() throws Exception {
        var algorithm = "naive";
        int initial = nextInteger();

        var primes = List.of(nextInteger(), nextInteger());
        PrimeResponseDto primeResponseDto = newPrimeResponseDtoBuilder()
                .withInitial(initial)
                .withPrimes(primes)
                .build();

        var primeGenerator = mock(PrimeGenerator.class);
        when(primeGenerator.generatePrimes(initial)).thenReturn(primeResponseDto.getPrimes());

        when(primeGeneratorStrategySelector.getPrimeGenerator(algorithm)).thenReturn(primeGenerator);
        doNothing().when(primeResourceValidator).validate(initial);

        var expected = asXmlString(primeResponseDto);

        mockMvc.perform(get(String.format("/api/v1/primes/%d/?algorithm=%s", initial, algorithm))
                .accept(APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void shouldThrowValidationExceptionWhenNegativeNumber() throws Exception {
        var name = randomString();
        var message = randomString();

        var validationErrors = new ValidationError();
        validationErrors.addError(name, message);

        int initial = -1;
        doThrow(new ValidationException(validationErrors)).when(primeResourceValidator).validate(initial);

        mockMvc.perform(get("/api/v1/primes/" + initial)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(asJsonString(validationErrors))));
    }

}