package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(3);
        Thread t1 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.printf("Start %s thread%n", name);
            barrier.await();
            System.out.printf("Thread %s finished, count = %s%n", name, barrier.count);
        }, "First");
        Thread t2 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.printf("Start %s thread%n", name);
            barrier.await();
            System.out.printf("Thread %s finished, count = %s%n", name, barrier.count);
        }, "Second");
        t1.start();
        t2.start();
        for (int i = 0; i < barrier.total; i++) {
            barrier.count();
            Thread.sleep(500);
        }
    }
}