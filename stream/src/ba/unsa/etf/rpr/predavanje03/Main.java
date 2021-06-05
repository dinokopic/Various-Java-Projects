package ba.unsa.etf.rpr.predavanje03;

public class Main {

    public static void main(String[] args) {
        PrimeNumbers primeNumbers = new PrimeNumbers();
        long startTime = System.nanoTime();
        primeNumbers.isPrime(1000000000);
        long endTime = System.nanoTime();

        System.out.println(endTime - startTime + " ns");
    }
}
