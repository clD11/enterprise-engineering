package com.enterprise_engineering.prime.generator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PrimeGeneratorEratosthenesTest {

    private final PrimeGenerator primeGenerator = new PrimeGeneratorEratosthenes();

    @Test
    void shouldReturnNoPrimesWhenLessThanNot2() {
        List<Integer> actual = primeGenerator.generatePrimes(1);
        assertThat(actual.size(), is(0));
    }

    @Test
    void shouldReturnPrimesInclusiveOf2() {
        List<Integer> expected = List.of(2);
        List<Integer> actual = primeGenerator.generatePrimes(2);
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnPrimesInclusiveOf3() {
        List<Integer> expected = List.of(2, 3);
        List<Integer> actual = primeGenerator.generatePrimes(3);
        assertThat(actual, is(expected));
    }

    @Test
    void shouldReturnPrimesInclusiveOfRange() {
        List<Integer> expected = List.of(2, 3, 5, 7, 11, 13, 17, 19);
        List<Integer> actual = primeGenerator.generatePrimes(20);
        assertThat(actual, is(expected));
    }

}