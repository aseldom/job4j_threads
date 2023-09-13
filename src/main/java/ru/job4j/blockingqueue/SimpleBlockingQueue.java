package ru.job4j.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int count;
    @GuardedBy("this")
    private boolean empty = true;
    @GuardedBy("this")
    private boolean full = false;

    public SimpleBlockingQueue(int count) {
        this.count = count;
        check();
    }

    private synchronized void check() {
        int size = getSize();
        empty = size == 0;
        full = size == count;
    }

    public synchronized int getSize() {
        return queue.size();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (full) {
            wait();
        }
        queue.offer(value);
        check();
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (empty) {
            wait();
        }
        T e = queue.poll();
        check();
        notifyAll();
        return e;
    }
}
