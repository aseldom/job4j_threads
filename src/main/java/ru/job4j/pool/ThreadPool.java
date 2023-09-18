package ru.job4j.pool;

import ru.job4j.blockingqueue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(2);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    new Thread(tasks.poll()).start();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < threadPool.size; i++) {
            threadPool.work(() -> System.out.println("Runs thread"));

        }
    }
}