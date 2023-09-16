package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void addUpdateDeleteTest() {
        Base b1 = new Base(1, 1);
        b1.setName("First");
        Base b2 = new Base(2, 1);
        b2.setName("Second");
        Base b3 = new Base(2, 1);
        b3.setName("Third");
        Base b4 = new Base(2, 1);
        b4.setName("Fourth");
        Cache cache = new Cache();
        assertThat(cache.add(b1)).isTrue();
        assertThat(cache.add(b1)).isFalse();
        assertThat(cache.add(b2)).isTrue();
        assertThat(cache.update(b3)).isTrue();
        assertThat(cache.getCache().get(1)).isEqualTo(b1);
        assertThat(cache.getCache().get(2).getName()).isEqualTo("Third");
        assertThat(cache.getCache().get(2).getVersion()).isEqualTo(2);
        assertThatThrownBy(() -> cache.update(b4)).isInstanceOf(OptimisticException.class);
        cache.delete(b1);
        assertThat(cache.getCache().get(1)).isEqualTo(null);
    }
}