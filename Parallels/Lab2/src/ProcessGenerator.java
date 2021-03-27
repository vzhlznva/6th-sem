class ProcessGenerator implements Runnable{

    CPUQueue queue1;
    CPUQueue queue2;//тип очередь
    int generateNumber;//число сгенерированных потоков
    CPU c1;
    CPU c2;
    int maxSize;
    public long generateDelay;

    ProcessGenerator(CPUQueue q1, CPUQueue q2, int gN, CPU c1, CPU c2){
        this.queue1 = q1; //присвоение очереди
        this.queue2 = q2; //присвоение очереди
        this.generateNumber = gN; //присвоение числа генерируемых потоков
        this.c1 = c1;
        this.c2 = c2;
    }

    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < generateNumber; i++) {
            int randMin=10;
            int randMax=40; // rand = [10,50]
            generateDelay = randMin + (int) (Math.random() * randMax); //делаем задержку от 10 до 50
            try { // определяет блок кода, в котором может быть исключение
                Thread.sleep(generateDelay);// ожидание времени generateDelay перед продолжением работы потока
            } catch (InterruptedException e) { // определяет блок кода, в котором происходит обработка исключения (прерванный поток)
                e.printStackTrace(); //вывод сообщения об ошибке в консоль
            }
            try {
                System.out.println("Process generated with delay " + generateDelay); //вывод "процесс был сгенерирован с задержкой: "
//                if (c1.isBusy() == false) {
//                    System.out.println("Process "+i+" goes to CPU1\n");
//                    c1.process();
////                    notifyAll();
//                } else if (c2.isBusy() == false){
//                    System.out.println("Process "+i+" goes to CPU2\n");
//                    c2.process();
////                    notifyAll();
//                } else
                    if (queue1.getSize() <= queue2.getSize()){
                    queue1.put("New process to queue1");
                    System.out.println("Process "+i+" goes to queue1\n");
                } else {
                    queue2.put("New process to queue2");
                    System.out.println("Process "+i+" goes to queue2\n");
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (queue1.getMaxSize()>=queue2.getMaxSize()){
            maxSize = queue1.getMaxSize();
        } else {
            maxSize = queue2.getMaxSize();
        }
        System.out.println("No more processes");
        System.out.println("Greatest queue1 size was " + queue1.getMaxSize());
        System.out.println("Greatest queue2 size was " + queue2.getMaxSize());
        System.out.println("Greatest queue size was " + maxSize);//конец генерации процессов и вывод максимальной длины очереди
    }
}

