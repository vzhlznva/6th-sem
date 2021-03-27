public class Main {

    public static void main(String[] args) {
        int queueCapacity = 5; //длина очереди
        int processToGenerate = 20; //количество процессов для генерации
        System.out.println("\nQueue capacity = [" + queueCapacity + "], will be generated " + processToGenerate + " processes\n");
        CPUQueue q1 = new CPUQueue(queueCapacity, 1); //создаем очередь
        CPUQueue q2 = new CPUQueue(queueCapacity, 2); //создаем очередь
        CPU C1 = new CPU(q1, 1); //создаем процессор
        CPU C2 = new CPU(q2, 2);
//        Thread C1Thread = new Thread(C1);
//        Thread C2Thread = new Thread(C2);
//        C1Thread.start();
//        C2Thread.start();
        ProcessGenerator Cp = new ProcessGenerator(q1, q2, processToGenerate, C1, C2); //создаем генератор процессов
        new Thread(Cp).start(); //запуск потока генератора процессов
        new Thread(C1).start(); //запуск потока процессора
        new Thread(C2).start();

    }
}