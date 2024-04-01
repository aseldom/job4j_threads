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
            System.out.println(Thread.currentThread().getName() + " execute");
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            System.out.println(Thread.currentThread().getName() + " get monitor");
            while (count < total) {
                try {
                    monitor.wait();
                    System.out.println(Thread.currentThread().getName() + " try notify");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " notify");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread threadAwait = new Thread(countBarrier::await, "Thread-Await-1");
        Thread threadAwait2 = new Thread(countBarrier::await, "Thread-Await-2");
        Thread threadAwait3 = new Thread(countBarrier::await, "Thread-Await-3");
        threadAwait.start();
        threadAwait2.start();
        threadAwait3.start();
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
        threadAwait.join();
        threadAwait2.join();
    }
}