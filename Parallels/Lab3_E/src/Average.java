import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Average {

    public Average(int N){
        int[] array = new Random().ints(N).toArray();
        double serialAverage = IntStream.of(array).average().orElseThrow();


        AtomicLong atomicAverage = new AtomicLong();
        IntStream.of(array).parallel().forEach(atomicAverage::addAndGet);

        System.out.println("Serial average:   " + serialAverage);
        System.out.println("Atomic average:   " + (double)atomicAverage.get()/ array.length + "\n");

    }

}
