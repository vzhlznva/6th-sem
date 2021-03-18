public class Main {

    public static void main(String[] args) {
        int queueCapacity = 5; //длина очереди
        int processToGenerate = 15; //количество процессов для генерации
        System.out.println("\nQueue capacity = [" + queueCapacity + "], will be generated " + processToGenerate + " processes\n");
        CPUQueue q = new CPUQueue(queueCapacity); //создаем очередь
        ProcessGenerator Cp = new ProcessGenerator(q, processToGenerate); //создаем генератор процессов
        CPU C = new CPU(q); //создаем процессор
        new Thread(Cp).start(); //запуск потока генератора процессов
        new Thread(C).start(); //запуск потока процессора
    }
}