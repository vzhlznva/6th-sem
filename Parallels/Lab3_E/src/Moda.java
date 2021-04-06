import java.util.Random;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.LongStream;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Moda {

    public Moda(int N){
        long[] array = new Random().longs(N, 0, 500).toArray();
        HashMap<Integer, Integer> counter = new HashMap<>();
        AtomicIntegerArray atomicCounter = new AtomicIntegerArray(500);

        LongStream.of(array).forEach(arrayElement -> counter.compute((int) arrayElement, (k, v) -> (v != null) ? v + 1 : 1));

        int serialModa = counter.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow().getKey();

        LongStream.of(array).parallel().forEach(arrayElement -> atomicCounter.incrementAndGet((int) arrayElement));

        HashMap<Integer, Integer> atomCounter = new HashMap<>();
        for (int i = 0; i < 500; i++){
            atomCounter.put(i, atomicCounter.get(i));
        }
        int atomicModa = atomCounter.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow().getKey();

        System.out.println("Serial Moda: " + serialModa);
        System.out.println("Atomic Moda: " + atomicModa);

        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(atomCounter.entrySet());
        sortedList.sort(Comparator.comparingInt(Map.Entry::getKey));
        int medianIndex = array.length / 2;
        int index = 0;
        long median = 0;
        for (Map.Entry<Integer, Integer> integerIntegerEntry : sortedList) {
            index += integerIntegerEntry.getValue();
            if (index >= medianIndex) {
                median = integerIntegerEntry.getKey();
                break;
            }
        }
        System.out.println("Median: " + median);
        System.out.println();
    }
}
