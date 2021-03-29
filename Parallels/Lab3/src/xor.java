import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class xor {

    public xor(int N){
        int[] array = new Random().ints(10000L * N).toArray();
        int serialXOR = serialXORFunc(array);
        int atomicXOR = atomicXORFunc(array);

        System.out.println("Serial XOR:   " + serialXOR);
        System.out.println("Atomic XOR:   " + atomicXOR + "\n");
    }

    private int serialXORFunc (int[] array){
        return IntStream.of(array).reduce((a, b) -> a ^ b).orElseThrow();
    }

    private int atomicXORFunc (int[] array){
        AtomicInteger atomic = new AtomicInteger();
        IntStream.of(array).parallel().forEach(arrayElement -> {
            int oldXOR;
            int newXOR;
            do {
                oldXOR = atomic.get();
                newXOR = oldXOR ^ arrayElement;
            } while (!atomic.compareAndSet(oldXOR, newXOR));
        });
        return atomic.get();
    }
}
