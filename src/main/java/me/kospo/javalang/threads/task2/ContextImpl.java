package me.kospo.javalang.threads.task2;

import java.util.HashSet;
import java.util.Set;

public class ContextImpl implements Context {
    private final Runnable callback;
    private final Set<Thread> threads = new HashSet<>();

//    private final AtomicInteger completed = new AtomicInteger(0);
//    private final AtomicInteger interrupted = new AtomicInteger(0);
//    private final AtomicInteger failed = new AtomicInteger(0);

    private int interrupted = 0;
    private int completed = 0;
    private int failed = 0;

    private boolean shouldCallback = true;

    public ContextImpl(Runnable callback, Runnable[] tasks) {
        this.callback = callback;

        for (Runnable task : tasks) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!Thread.currentThread().isInterrupted()) {
                        boolean ok = true;
                        try {
                            task.run();
                        } catch (Exception e) {
                            ok = false;
                            incrementFailed();
                        }

                        if (ok) {
                            incrementCompleted();
                        }
                    } else {
                        incrementInterrupted();
                    }

                    tryRunCallback();
                }
            }, "executor-" + task);

            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }
    }

    private synchronized void tryRunCallback() {
        if(isFinished() && shouldCallback) {
            callback.run();
            shouldCallback = false;
        }
    }

    private synchronized void incrementFailed() {
        failed++;
    }
    private synchronized void incrementInterrupted() {
        interrupted++;
    }
    private synchronized void incrementCompleted() {
        completed++;
    }

    @Override
    public synchronized int getCompletedTaskCount() {
        return completed;
    }

    @Override
    public synchronized int getFailedTaskCount() {
        return failed;
    }

    @Override
    public synchronized int getInterruptedTaskCount() {
        return interrupted;
    }

    @Override
    public void interrupt() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    @Override
    public synchronized boolean isFinished() {
        return getCompletedTaskCount() + getFailedTaskCount() + getInterruptedTaskCount() == threads.size();
    }
}
