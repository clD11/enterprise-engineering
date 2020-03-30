package com.enterprise_engineering.prime.generator;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class PrimeGeneratorStrategySelectorTest {

    private final PrimeGeneratorStrategySelector primeGeneratorStrategySelector = new PrimeGeneratorStrategySelector();

    @Test
    void shouldGetPrimeGeneratorEratosthenes() {
        assertThat(primeGeneratorStrategySelector.getPrimeGenerator("eratosthenes"),
                is(instanceOf(PrimeGeneratorEratosthenes.class)));
    }

    @Test
    void shouldGetPrimeGeneratorNaive() {
        assertThat(primeGeneratorStrategySelector.getPrimeGenerator("naive"),
                is(instanceOf(PrimeGeneratorNaive.class)));
    }

}