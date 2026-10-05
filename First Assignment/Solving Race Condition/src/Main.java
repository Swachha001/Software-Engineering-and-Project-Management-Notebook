public class Main {
    private static final int THREADS = 3;
    private static final int INCREMENTS = 100000;

    public static void main(String[] args) throws InterruptedException {
        M1[] synchronizedThreads = {
                new M1(), new M1(), new M1()
        };

        startAndWait(synchronizedThreads);
        System.out.println("--- Using synchronized block ---");
        System.out.println("Expected count: " + (THREADS * INCREMENTS));
        System.out.println("Actual count: " + M1.count);

        M2[] atomicThreads = {
                new M2(), new M2(), new M2()
        };

        startAndWait(atomicThreads);
        System.out.println("\n--- Using AtomicInteger ---");
        System.out.println("Expected count: " + (THREADS * INCREMENTS));
        System.out.println("Actual count: " + M2.count.get());
    }

    private static void startAndWait(Thread[] threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
