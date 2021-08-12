import java.util.Random;
import java.util.stream.IntStream;
import java.util.concurrent.atomic.AtomicInteger;

public class XOR {

    public XOR(int N){
        int[] array = new Random().ints(N).toArray();
        int serialXOR = IntStream.of(array).reduce((a, b) -> a ^ b).getAsInt();

        AtomicInteger atomicXOR = new AtomicInteger();
        IntStream.of(array).parallel().forEach(arrayElement -> atomicXOR.accumulateAndGet(arrayElement, (a, b) -> a ^ b));

        System.out.println("SerialXOR: " + serialXOR);
        System.out.println("AtomicXOR: " + atomicXOR);
        System.out.println();
    }


}
