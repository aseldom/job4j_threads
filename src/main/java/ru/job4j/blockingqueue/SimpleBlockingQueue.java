package ru.job4j.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int total;

    public SimpleBlockingQueue(int total) {
        this.total = total;
    }

    public synchronized int getSize() {
        return queue.size();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (getSize() == total) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (getSize() == 0) {
            wait();
        }
        T e = queue.poll();
        notifyAll();
        return e;
    }
}
