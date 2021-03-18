import java.util.LinkedList; //двусвязный список
import java.util.Queue; //очередь

class CPUQueue {

    private Queue<String> queue = new LinkedList<>(); //создание очереди
    private int capacity; // длина очереди
    private int maxSize = 0; //макс. размер

    public CPUQueue(int capacity) {
        this.capacity = capacity;
    }
    public int getMaxSize() {
        return maxSize; //возвращение макс. длины
    }
    public synchronized void put(String element) throws InterruptedException {
        while(queue.size() == capacity) {
            System.out.println("Queue is FULL, waiting.."); //если очередь полная, то ждать
            wait();
        }
        queue.add(element); //добавить элемент в очередь

        if(queue.size()>maxSize)
            maxSize=queue.size(); //проверка на макс. размер

        System.out.println("Process added, queue size = [" + queue.size() + "]\n");
        notify(); // notifyAll() for multiple CPU/CPUProcess threads
    }

    public synchronized String get() throws InterruptedException {
        while(queue.isEmpty()) {
            System.out.println("Queue is EMPTY, waiting..");
            wait();
        }
        String item = queue.remove();
        System.out.println("Process removed, queue size = [" + queue.size() + "]");
        notify(); // notifyAll() for multiple CPU/CPUProcess threads
        return item;
    }
}

