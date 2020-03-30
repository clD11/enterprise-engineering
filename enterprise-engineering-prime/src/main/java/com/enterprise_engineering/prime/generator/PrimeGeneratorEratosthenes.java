package com.enterprise_engineering.prime.generator;

import java.util.ArrayList;
import java.util.List;

public class PrimeGeneratorEratosthenes implements PrimeGenerator {

    @Override
    public List<Integer> generatePrimes(int initial) {
        List<Integer> primes = new ArrayList<>();

        boolean[] isPrime = new boolean[initial + 1];
        for (int i = 2; i <= initial; i++) {
            isPrime[i] = true;
        }

        for (int factor = 2; factor * factor <= initial; factor++) {
            if (isPrime[factor]) {
                for (int j = factor; factor*j <= initial; j++) {
                    isPrime[factor*j] = false;
                }
            }
        }

        for (int i = 2; i <= initial; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }
}
