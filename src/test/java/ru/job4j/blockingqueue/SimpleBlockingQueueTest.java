package ru.job4j.blockingqueue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenIt() throws InterruptedException {
        SimpleBlockingQueue<Integer> list = new SimpleBlockingQueue<>(2);
        Thread consumer = new Thread(() -> {
            try {
                list.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread producer = new Thread(() -> {
            try {
                list.offer(1);
                list.offer(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        consumer.start();
        Thread.sleep(500);
        producer.start();
        consumer.join();
        producer.join();
        assertThat(list.getSize()).isEqualTo(1);
        assertThat(list.poll()).isEqualTo(2);
    }

    @Test
    public void whenAdd() throws InterruptedException {
        SimpleBlockingQueue<Integer> list = new SimpleBlockingQueue<>(1);
        final int[] in = {1, 2, 3};
        final int[] res = new int[3];
        Thread producer = new Thread(() -> {
            try {
                list.offer(in[0]);
                list.offer(in[1]);
                list.offer(in[2]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                res[0] = list.poll();
                res[1] = list.poll();
                res[2] = list.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list.getSize()).isEqualTo(0);
        assertThat(res).containsExactly(in);
    }
}