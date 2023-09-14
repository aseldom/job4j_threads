package ru.job4j.buffer;

import ru.job4j.blockingqueue.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ).start();

        while (true) {
            boolean ended = false;
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getState() == Thread.State.TERMINATED) {
                    ended = true;
                    break;
                }
                Thread.sleep(100);
            }
            if (ended) {
                consumer.interrupt();
                break;
            }
        }
    }
}