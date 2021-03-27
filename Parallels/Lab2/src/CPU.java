class CPU implements Runnable{

    CPUQueue queue;
    int cpuId;

    CPU(CPUQueue q, int cpuId){
        this.queue = q;
        this.cpuId = cpuId;
    }

    public boolean busy = false;

    public void run(){
        long processingTime; //время обработки
        while(true) {
//            if (busy) {
//                int randMin = 20;
//                int randMax = 80; // r
//                // and = [20,100]
//                processingTime = randMin + (int) (Math.random() * randMax);
//                System.out.println("Is processing without queue...");
//                try {
//                    Thread.sleep(processingTime);
//                    busy = false;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Without QUEUE: CPU"+cpuId+": Processed in time " + processingTime + "\n");
//            } else
                if (queue.is_Empty()==false){
                int randMin = 20;
                int randMax = 80; // r
                // and = [20,100]
                processingTime = randMin + (int) (Math.random() * randMax);
                try {
//                    busy = true;
                    queue.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try{
                    Thread.sleep(processingTime);
//                    busy = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("From QUEUE: CPU"+cpuId+": Processed in time " + processingTime + "\n");
            }
        }
    }

    public boolean isBusy(){
        return busy;
    }

    public synchronized void process(){
        busy = true;
    }
}

