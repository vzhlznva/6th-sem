import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.Random;

public class minMax {

    public minMax(int N){
        long[] array = new Random().longs(10000L*N).toArray();
        long serialMin = serialMinFunc(array);
        long serialMax = serialMaxFunc(array);
        long atomMin = atomicMinFunc(array);
        long atomMax = atomicMaxFunc(array);

        System.out.println("Serial min:   " + serialMin);
        System.out.println("Atomic min:   " + atomMin);

        System.out.println("Serial max:   " + serialMax);
        System.out.println("Atomic max:   " + atomMax + "\n");
    }

    private long serialMinFunc(long[] array){
        return LongStream.of(array).min().orElseThrow();
    }

    private long serialMaxFunc(long[] array){
        return LongStream.of(array).max().orElseThrow();
    }

    private long atomicMinFunc(long[] array){
        AtomicLong min = new AtomicLong(Long.MAX_VALUE);
        LongStream.of(array).parallel().forEach( arrayElement -> {
            long oldMin;
            long newMin;
            do{
                oldMin = min.get();
                if (arrayElement < oldMin){
                    newMin = arrayElement;
                }
                else {
                    newMin = oldMin;
                }
            }while(!min.compareAndSet(oldMin, newMin));
        });
        return min.get();
    }

    private long atomicMaxFunc(long[] array){
        AtomicLong max = new AtomicLong(Long.MIN_VALUE);
        LongStream.of(array).parallel().forEach( arrayElement -> {
            long oldMax;
            long newMax;
            do{
                oldMax = max.get();
                if (arrayElement > oldMax){
                    newMax = arrayElement;
                }
                else {
                    newMax = oldMax;
                }
            }while(!max.compareAndSet(oldMax, newMax));
        });
        return max.get();
    }
}
