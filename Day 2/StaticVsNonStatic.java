public class StaticVsNonStatic {

    // Static variable - class level
    static volatile long staticCount = 0;

    // Non-static variable - object level
    volatile long nonStaticCount = 0;

    // Running flag - volatile must
    static volatile boolean running = true;

    public static void main(String[] args) throws Exception {

        // ========== CASE 1: 1 minute ==========
        runCase(1, 1);

        // ========== CASE 2: 5 minutes ==========
        // runCase(2, 5);

        // ========== CASE 3: 15 minutes ==========
        // runCase(3, 15);
    }

    static void runCase(int caseNo, int min) throws Exception {

        staticCount = 0;
        running = true;
        StaticVsNonStatic obj = new StaticVsNonStatic();

        // Static count thread
        Thread t1 = new Thread(() -> {
            while (running) {
                staticCount++;
            }
        });

        // Non-static count thread
        Thread t2 = new Thread(() -> {
            while (running) {
                obj.nonStaticCount++;
            }
        });

        t1.start();
        t2.start();

        int milliseconds = min * 60 * 1000;
        Thread.sleep(milliseconds);

        running = false;

        t1.join();
        t2.join();

        // Result
        long s = staticCount;
        long n = obj.nonStaticCount;
        double diff = (s - n) * 100.0 / s;

        System.out.println("Case " + caseNo + " (" + min + " min)");
        System.out.println("  Static count     : " + s);
        System.out.println("  Non-static count : " + n);
        System.out.printf ("  Non-static pichiye : %.4f%%%n", diff);
        System.out.println();
    }
}