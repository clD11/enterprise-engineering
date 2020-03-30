package com.enterprise_engineering.web.resource;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.enterprise_engineering.test_supprt.random.RandomUtil.nextInteger;
import static com.enterprise_engineering.web.resource.PrimeResponseDto.PrimeResponseDtoBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PrimeResponseDtoTest {

    @Test
    void shouldBuildPrimeResponse() {
        int initial = nextInteger();
        List<Integer> primes = List.of(nextInteger(), nextInteger());

        var expected = PrimeResponseDtoBuilder.newPrimeResponseDtoBuilder()
                .withInitial(initial)
                .withPrimes(primes)
                .build();

        assertThat(initial, is(expected.getInitial()));
        assertThat(primes, is(expected.getPrimes()));
    }

}