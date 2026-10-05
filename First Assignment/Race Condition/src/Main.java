public class Main {
    public static void main(String[] args) throws InterruptedException {
        Mythreads[] workers = {
                new Mythreads(),
                new Mythreads(),
                new Mythreads()
        };

        for (Mythreads worker : workers) {
            worker.start();
        }

        for (Mythreads worker : workers) {
            worker.join();
        }

        int expected = workers.length * 100000;
        System.out.println("Expected count: " + expected);
        System.out.println("Actual count: " + Mythreads.count);
    }
}
