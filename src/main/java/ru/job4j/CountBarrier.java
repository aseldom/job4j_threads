package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;

    private final int total;
    @GuardedBy("this")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (!(count >= total)) {
                try {
                    monitor.wait();
                    System.out.println(Thread.currentThread().getName() + " try notify");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " notify");
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread threadAwait = new Thread(countBarrier::await, "Thread-Await");
        threadAwait.start();
        for (int i = 1; i <= countBarrier.total; i++) {
            Thread threadCount = new Thread(countBarrier::count);
            System.out.println(threadCount.getName());
            threadCount.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}