package com.enterprise_engineering.prime.generator;

import com.google.common.math.IntMath;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;

public class PrimeGeneratorParallel implements PrimeGenerator {

    private final static int PARALLELISM_LEVEL = 4;

    @Override
    public List<Integer> generatePrimes(int initial) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(PARALLELISM_LEVEL);
        List<Integer> primes;
        try {
            primes = forkJoinPool.submit(() -> rangeClosed(0, initial).parallel()
                    .filter(IntMath::isPrime).boxed().collect(Collectors.toList())).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            forkJoinPool.shutdown();
        }
        return primes;
    }

}

