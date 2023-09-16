package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void sumTest() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                casCount.increment();
                casCount.get();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                casCount.increment();
                casCount.get();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertThat(casCount.get()).isEqualTo(20000);
    }
}