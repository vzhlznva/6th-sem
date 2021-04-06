import java.util.Random;
import java.util.stream.LongStream;
import java.util.concurrent.atomic.AtomicLong;

public class MinMax {

    public MinMax(int N){
        long[] array = new Random().longs(N).toArray();
        long serialMin = LongStream.of(array).min().getAsLong();
        long serialMax = LongStream.of(array).max().getAsLong();

        AtomicLong atomicMax = new AtomicLong(Long.MIN_VALUE);
        AtomicLong atomicMin = new AtomicLong(Long.MAX_VALUE);

        LongStream.of(array).parallel().forEach(arrayElement -> {
            atomicMax.accumulateAndGet(arrayElement, Math::max);
            atomicMin.accumulateAndGet(arrayElement, Math::min);
        });

        System.out.println("Serial min: " + serialMin);
        System.out.println("Atomic min: " + atomicMin.get() + "\n");

        System.out.println("Serial max: " + serialMax);
        System.out.println("Atomic max: " + atomicMax.get() + "\n");


    }
}
