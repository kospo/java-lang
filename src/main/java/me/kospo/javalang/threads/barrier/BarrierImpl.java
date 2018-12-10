package me.kospo.javalang.threads.barrier;

public class BarrierImpl implements Barrier {
    private final int threadCount;
    private int threadsWaiting = 0;

    public BarrierImpl(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public synchronized void await() {
        threadsWaiting++;
        
        while (threadsWaiting < threadCount) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.notifyAll();
    }

}
