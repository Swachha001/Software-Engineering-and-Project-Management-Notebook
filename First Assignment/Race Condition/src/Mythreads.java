public class Mythreads extends Thread {
    static int count = 0;
    private static final int LIMIT = 100000;

    @Override
    public void run() {
        for (int i = 0; i < LIMIT; i++) {
            addOne();
        }
    }

    private void addOne() {
        count++;
    }
}
