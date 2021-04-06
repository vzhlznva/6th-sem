import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Scalar {

    public Scalar(int N){

        int[] vect1 = new Random().ints(N, 0, 500).toArray();
        int[] vect2 = new Random().ints(N, 0, 500).toArray();

        AtomicInteger atomicScalar = new AtomicInteger();

        int serialScalar = IntStream.range(0, vect1.length).map(i -> vect1[i] * vect2[i]).sum();
        IntStream.range(0, vect1.length).parallel().forEach(i -> atomicScalar.updateAndGet(prev -> prev + vect1[i] * vect2[i]));

        System.out.println("Serial Scalar: " + serialScalar);
        System.out.println("Atomic Scalar: " + atomicScalar.get());
        System.out.println();
    }
}
