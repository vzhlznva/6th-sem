import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class average {

    public average(int N){
        int[] array = new Random().ints(10000L * N).toArray();
        double serialAverage = serialAvgFunc(array);
        double atomicAverage = atomicAvgFunc(array);

        System.out.println("Serial average:   " + serialAverage);
        System.out.println("Atomic average:   " + atomicAverage + "\n");
    }

    private double serialAvgFunc(int[] array){
        return IntStream.of(array).average().orElseThrow();
    }

    private double atomicAvgFunc(int[] array){
        AtomicLong sum = new AtomicLong();
        IntStream.of(array).parallel().forEach(arrayElement -> {
            long oldSum;
            long newSum;
            do {
                oldSum = sum.get();
                newSum = oldSum + arrayElement;
            } while (!sum.compareAndSet(oldSum, newSum));
        });
        return (double) sum.get() / array.length;
    }
}
