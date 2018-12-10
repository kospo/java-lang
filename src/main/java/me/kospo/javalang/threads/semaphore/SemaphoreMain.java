package me.kospo.javalang.threads.semaphore;

public class SemaphoreMain {
    private final Semaphore semaphore = new SemaphoreImpl(4);

    public static void main(String[] args) {
        SemaphoreMain x = new SemaphoreMain();
        for (int i = 0; i < 11; i++) {
            Thread t = new Thread(() -> {
                x.run();
            });
            t.start();
        }

    }

    private void run() {
        semaphore.acquire();
        try {
            doLogic();
        } finally {
            semaphore.release();
        }
    }

    private void doLogic() {
        System.out.println("Hello " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
