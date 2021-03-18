class CPU implements Runnable{

    CPUQueue queue;
    CPU(CPUQueue q){
        this.queue = q;
    }
    public void run(){
        long processingTime;
        while(true) {
            int randMin=20;
            int randMax=80; // rand = [20,100]
            processingTime = randMin + (int) (Math.random() * randMax);
            try {
                queue.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("CPU: Processed in time " + processingTime + "\n");
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

