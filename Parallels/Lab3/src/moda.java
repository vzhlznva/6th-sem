import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class moda {

    public moda(int N){
        long[] array = new Random().longs(10000L * N, 0, 1000).toArray();
        int[] freqArr = new int[1000];
        int serialModa = serialModaFunc(array);
        int atomicModa = atomicModaFunc(array, freqArr);
        long median = medianFunc(array, freqArr);

        System.out.println("Median:   " + median);
        System.out.println("Serial moda:   " + serialModa);
        System.out.println("Atomic moda:   " + atomicModa + "\n");
    }

    private int serialModaFunc(long[] array){
        int[] freq = new int[1000];
        LongStream.of(array).forEach(arrayElement -> {
            freq[(int) arrayElement]++;
        });
        return Arrays.stream(freq).boxed().collect(Collectors.toList()).indexOf(Arrays.stream(freq).max().orElseThrow());
    }

    private int atomicModaFunc(long[] array, int[] freqArr){
        AtomicIntegerArray atomicFreqArr = new AtomicIntegerArray(1000);
        LongStream.of(array).parallel().forEach( arrayElement -> {
            int oldValue;
            int newValue;
            do{
                oldValue = atomicFreqArr.get((int) arrayElement);
                newValue = oldValue + 1;
            }while(!atomicFreqArr.compareAndSet((int) arrayElement, oldValue, newValue));
        });

        for(int i=0; i < 1000; i++){
            freqArr[i] = atomicFreqArr.get(i);
        }
        return Arrays.stream(freqArr).boxed().collect(Collectors.toList()).indexOf(Arrays.stream(freqArr).max().orElseThrow());
    }

    private long medianFunc(long[] array, int[] freqArr){
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i=0; i < 1000; i++){
            hashMap.put(i, freqArr[i]);
        }

        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(hashMap.entrySet());
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
        return median;
    }
}
