# Java Multithreading — Race Condition

This assignment demonstrates a **race condition in Java multithreading** and two ways to solve it:

1. `synchronized`
2. `AtomicInteger`

## 📌 Assignment Problem

Three threads share one integer counter. Each thread increases the counter **100,000 times**.

```text
Number of threads = 3
Increments per thread = 100,000

Expected count = 3 × 100,000
               = 300,000
```

Because the threads access the same variable at the same time, the counter can suffer from a **race condition**.

---

# 🔴 Race Condition

The operation below is not thread-safe:

```java
count++;
```

It looks like one operation, but internally it involves reading the old value, adding one, and writing the new value. Two threads can interfere with each other, so some increments may be lost.

The exact result may vary between executions.

### `Mythreads.java`

```java
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
```

### `Main.java`

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Mythreads[] workers = {
                new Mythreads(), new Mythreads(), new Mythreads()
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
```

---

# 🟢 Solution 1 — `synchronized`

A synchronized block allows only one thread at a time to modify the shared counter.

### `M1.java`

```java
public class M1 extends Thread {
    static int count = 0;
    private static final int LIMIT = 100000;

    @Override
    public void run() {
        for (int i = 0; i < LIMIT; i++) {
            synchronized (M1.class) {
                count++;
            }
        }
    }
}
```

The important part is:

```java
synchronized (M1.class) {
    count++;
}
```

Only one thread can enter this block at a time.

---

# 🟢 Solution 2 — `AtomicInteger`

`AtomicInteger` provides thread-safe operations on an integer without explicitly writing a synchronized block.

### `M2.java`

```java
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
```

`addAndGet(1)` atomically adds 1 and returns the updated value.

---

# 🔍 Important Methods

### `start()`

Starts a thread and causes its `run()` method to execute.

```java
thread.start();
```

### `join()`

Makes the main thread wait until another thread finishes.

```java
thread.join();
```

`join()` does **not** solve a race condition. It only makes sure the worker threads have finished before the final result is printed.

### `synchronized`

Provides mutual exclusion so that only one thread can execute the protected section at a time.

### `AtomicInteger`

Provides atomic, thread-safe operations such as:

```java
count.addAndGet(1);
```

and:

```java
count.get();
```

---

# ⚖️ `synchronized` vs `AtomicInteger`

| Feature | `synchronized` | `AtomicInteger` |
|---|---|---|
| Thread-safe | ✅ | ✅ |
| Prevents race condition | ✅ | ✅ |
| Uses explicit locking | ✅ | ❌ |
| Suitable for counters | ✅ | ✅ |
| Increment style | `count++` inside synchronized block | `addAndGet(1)` |
| Easy to understand | ✅ | ✅ |

## 🧠 Key Takeaways

* Multiple threads accessing shared data can cause a **race condition**.
* `count++` is not an atomic operation.
* `join()` waits for a thread to finish but does not protect shared data.
* `synchronized` provides mutual exclusion.
* `AtomicInteger` provides atomic integer operations.
* Both solutions safely produce the expected count of **300,000**.

### Important distinction

```text
join()
  ↓
Wait for a thread to finish

synchronized
  ↓
Allow one thread at a time in the protected section

AtomicInteger
  ↓
Perform integer operations atomically
```
