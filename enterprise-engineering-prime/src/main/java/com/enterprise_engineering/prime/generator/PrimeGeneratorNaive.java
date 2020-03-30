package com.enterprise_engineering.prime.generator;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.math.IntMath.isPrime;

public class PrimeGeneratorNaive implements PrimeGenerator {

    @Override
    public List<Integer> generatePrimes(int initial) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 0; i <= initial; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }
}
