package com.enterprise_engineering.web.resource;

import com.enterprise_engineering.prime.generator.PrimeGenerator;
import com.enterprise_engineering.prime.generator.PrimeGeneratorStrategySelector;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

import static com.enterprise_engineering.web.resource.PrimeResponseDto.PrimeResponseDtoBuilder.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = "/api/v1/primes")
public class PrimeResource {

    private final PrimeResourceValidator primeResourceValidator;
    private final PrimeGeneratorStrategySelector primeGeneratorStrategySelector;

    PrimeResource(PrimeResourceValidator primeResourceValidator, PrimeGeneratorStrategySelector primeGeneratorStrategySelector) {
        this.primeResourceValidator = primeResourceValidator;
        this.primeGeneratorStrategySelector = primeGeneratorStrategySelector;
    }

    @GetMapping(value = "/{initial}", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    ResponseEntity<PrimeResponseDto> getPrimes(@PathVariable int initial,
                                               @RequestParam(defaultValue = "eratosthenes", required = false) String algorithm) {
        primeResourceValidator.validate(initial);
        PrimeGenerator primeGenerator = primeGeneratorStrategySelector.getPrimeGenerator(algorithm);
        PrimeResponseDto primeResponseDto = newPrimeResponseDtoBuilder()
                .withInitial(initial)
                .withPrimes(primeGenerator.generatePrimes(initial))
                .build();
        return new ResponseEntity<>(primeResponseDto, HttpStatus.OK);
    }

    @ModelAttribute
    public void setCacheControlHeader(HttpServletResponse response) {
        String headerValue = CacheControl.maxAge(365, TimeUnit.DAYS).getHeaderValue();
        response.addHeader(HttpHeaders.CACHE_CONTROL, headerValue);
    }

}