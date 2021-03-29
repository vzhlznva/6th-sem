import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.Random;

public class scalarProduct {

    public scalarProduct(int N){
        int[] array1 = new Random().ints(10000L*N, 0, 100).toArray();
        int[] array2 = new Random().ints(10000L*N, 0, 100).toArray();
        long serialScalar = serialProduct(array1, array2);
        long atomicScalar = atomicProduct(array1, array2);

        System.out.println("Serial scalar:   " + serialScalar);
        System.out.println("Atomic scalar:   " + atomicScalar + "\n");
    }

    private long serialProduct(int[] array1, int[] array2){
        return IntStream.range(0, array1.length).mapToLong(index -> (long) array1[index] * array2[index]).sum();
    }

    private long atomicProduct(int[] array1, int[] array2){
        AtomicLong scalar = new AtomicLong();
        IntStream.range(0, array1.length).parallel().forEach(index -> {
            long oldScalar;
            long newScalar;
            do {
                oldScalar = scalar.get();
                newScalar = oldScalar + (long) array1[index] * array2[index];
            } while (!scalar.compareAndSet(oldScalar, newScalar));
        });
        return scalar.get();
    }
}
