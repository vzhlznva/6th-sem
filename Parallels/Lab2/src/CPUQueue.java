import java.util.LinkedList; //двусвязный список
import java.util.Queue; //очередь


class CPUQueue {

    private Queue<String> queue = new LinkedList<>(); //создание очереди
    private int capacity; // длина очереди
    private int maxSize = 0; //макс. размер
    private int queueId;
    private boolean isEmpty = true;

    public CPUQueue(int capacity, int queueId) {
        this.capacity = capacity;
        this.queueId = queueId;
    }

    public int getMaxSize() {
        return maxSize; //возвращение макс. длины
    }

    public int getSize(){return queue.size();}

    public boolean is_Empty() {
        if (queue.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void put(String element) throws InterruptedException {

        queue.add(element); //добавить элемент в очередь

        if(queue.size()>maxSize)
            maxSize=queue.size(); //проверка на макс. размер

        System.out.println("Process added, queue"+queueId+" size = [" + queue.size() + "]\n"); // вывод размера очереди
    }

    public synchronized String get() throws InterruptedException {
        while (queue.isEmpty()){
            System.out.println("Queue"+queueId+" is EMPTY, waiting...");
            wait();
        }
        String item = queue.remove(); //удаление из очереди
        System.out.println("Process removed, queue"+queueId+" size = [" + queue.size() + "]"); //вывод размера очереди
        notifyAll();
        return item;
    }
}

