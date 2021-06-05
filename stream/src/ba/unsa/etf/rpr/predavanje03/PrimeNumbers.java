package ba.unsa.etf.rpr.predavanje03;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PrimeNumbers {

    private LongStream primes;

    public PrimeNumbers() {
        primes = LongStream.iterate(2, number -> number+1)
                .filter(PrimeNumbers::isNumberPrime);
    }

    private static boolean isNumberPrime(long number) {
        for (long i = 2; i <= Math.sqrt(number); i++)
            if (number % i == 0) return false;
        return true;
    }

    public boolean isPrime(long number) {
          return primes.filter(x -> x >= number)
                  .findFirst()
                  .getAsLong() == number;
    }

    public int numberOfPrimesLesserThan(long number) {
        return (int) primes.takeWhile(x -> x < number)
                .count();
    }

    public long nthPrime(int n) {
        return primes.skip(n-1)
                .findFirst()
                .getAsLong();
    }

    public List<Long> getFirstNPrimes(int n) {
        return primes.limit(n)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Long> getPrimesBetween(long lowerBound, long upperBound) {
        return primes.filter(x -> x >= lowerBound)
                .takeWhile(x-> x <= upperBound)
                .boxed()
                .collect(Collectors.toList());
    }
}
