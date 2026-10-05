import java.util.concurrent.atomic.AtomicInteger;

public class M2 extends Thread {
    static final AtomicInteger count = new AtomicInteger();
    private static final int LIMIT = 100000;

    @Override
    public void run() {
        for (int i = 0; i < LIMIT; i++) {
            count.addAndGet(1);
        }
    }
}
