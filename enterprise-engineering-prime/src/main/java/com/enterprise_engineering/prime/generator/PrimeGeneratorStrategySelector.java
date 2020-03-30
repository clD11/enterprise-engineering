package com.enterprise_engineering.prime.generator;

import com.google.common.collect.ImmutableMap;

public class PrimeGeneratorStrategySelector {

    private final ImmutableMap<String, PrimeGenerator> primeNumberAlgorithms =
            ImmutableMap.of("eratosthenes", new PrimeGeneratorEratosthenes(),
                    "naive", new PrimeGeneratorNaive(), "parallel", new PrimeGeneratorParallel());

    public PrimeGenerator getPrimeGenerator(String algorithm) {
        return primeNumberAlgorithms.get(algorithm);
    }

}
