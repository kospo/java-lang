package me.kospo.javalang.threads.task2;

public class TaskMain {
    public static void main(String[] args) {
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                System.out.println("CALLBACK");
            }
        };

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
//                sleep(520);
                System.out.println("1");
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
//                sleep(510);
                for (int i = 0; i < 1_000_000_000; i++) {

                }
//                System.out.println("2");
            }
        };

        Runnable r3 = new Runnable() {
            @Override
            public void run() {
//                sleep(500);
                System.out.println("3");
                throw new RuntimeException("!!!");
            }
        };

        ExecutionManager e = new ExecutionManagerImpl();
        Context c = e.execute(callback, r1, r2, r3);
//        sleep(515);
        while (!c.isFinished()) {
//            System.out.println("---");
//            System.out.println("c.getCompletedTaskCount() = " + c.getCompletedTaskCount());
//            System.out.println("c.getFailedTaskCount() = " + c.getFailedTaskCount());
//            System.out.println("c.getInterruptedTaskCount() = " + c.getInterruptedTaskCount());
            c.interrupt();
        }
        System.out.println("c.getCompletedTaskCount() = " + c.getCompletedTaskCount());
        System.out.println("c.getFailedTaskCount() = " + c.getFailedTaskCount());
        System.out.println("c.getInterruptedTaskCount() = " + c.getInterruptedTaskCount());
    }

    private static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
