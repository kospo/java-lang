package me.kospo.javalang.threads.barrier;

public class BarrierMain {
    private static final int RUNNERS_NUM = 3;
    public static final double START = System.nanoTime();
    private final Barrier barrier = new BarrierImpl(RUNNERS_NUM);

    private static class Runner {
        private final Barrier barrier;
        private final double speed;
        private final double distance;
        private long startTime;

        private Runner(Barrier barrier, double speed, double distance) {
            this.barrier = barrier;
            this.speed = speed;
            this.distance = distance;
        }

        public void run() {
            doRun();
            barrier.await();
            printFinish();
        }

        private void printFinish() {
            double runningTime = (System.currentTimeMillis() - startTime) / 1E3;

            System.out.println("finish " + Thread.currentThread().getName() + " : runningTime=" + runningTime);
        }

        private void doRun() {
            startTime = System.currentTimeMillis();

            double position = 0;
            while(position < distance) {
                position += speed;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        BarrierMain main = new BarrierMain();

        for (int i = 0; i < RUNNERS_NUM; i++) {
            int speed = 10;
            int distance = 10 + i * 10;
            Runner rz = new Runner(main.barrier, speed, distance);

            Thread t = new Thread(rz::run, "i=" + i);
            t.start();
        }
    }
}
