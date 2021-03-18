class ProcessGenerator implements Runnable{

    CPUQueue queue; //тип очередь
    int generateNumber; //число сгенерированных потоков
    ProcessGenerator(CPUQueue q, int gN){
        this.queue = q; //присвоение очереди
        this.generateNumber = gN; //присвоение числа генерируемых потоков
    }
    public void run(){
        long generateDelay; //задержка при генерации
        for (int i = 0; i < generateNumber; i++) {
            int randMin=10;
            int randMax=40; // rand = [10,50]
            generateDelay = randMin + (int) (Math.random() * randMax); //делаем задержку от 10 до 50
            try { // определяет блок кода, в котором может быть исключение
                Thread.sleep(generateDelay); // ожидание времени generateDelay перед продолжением работы потока
            } catch (InterruptedException e) { // определяет блок кода, в котором происходит обработка исключения (прерванный поток)
                e.printStackTrace(); //вывод сообщения об ошибке в консоль
            }
            try {
                System.out.println("Process generated with delay " + generateDelay); //вывод "процесс был сгенерирован с задержкой: "
                queue.put("New process"); //запихиваем процесс в очередь
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("No more processes. Greatest queue size was " + queue.getMaxSize()); //конец генерации процессов и вывод максимальной длины очереди
    }
}

