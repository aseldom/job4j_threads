package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void linearStringSearchThen4() {
        String[] s = {"d", "a", "a", "b", "c", "g"};
        ParallelSearch<String> ps = new ParallelSearch<>(s, "c", 0, s.length - 1);
        assertThat(ps.search()).isEqualTo(4);
    }

    @Test
    public void linearStringSearchThenNotFinding() {
        String[] s = {"d", "a", "a", "b", "g", "g"};
        ParallelSearch<String> ps = new ParallelSearch<>(s, "c", 0, s.length - 1);
        assertThat(ps.search()).isEqualTo(-1);
    }

    @Test
    public void parallelStringSearchThen11() {
        String[] s = {"we", "d", "a", "a", "b", "c", "g", "ee", "ww", "ww", "re", "get"};
        ParallelSearch<String> ps = new ParallelSearch<>(s, "get", 0, s.length - 1);
        assertThat(ps.search()).isEqualTo(11);
    }

    @Test
    public void ParallelIntSearchThen3() {
        Integer[] s = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ParallelSearch<Integer> ps = new ParallelSearch<>(s, 4, 0, s.length - 1);
        assertThat(ps.search()).isEqualTo(3);
    }

    @Test
    public void ParallelIntSearchThenNotFinding() {
        Integer[] s = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ParallelSearch<Integer> ps = new ParallelSearch<>(s, 99, 0, s.length - 1);
        assertThat(ps.search()).isEqualTo(-1);
    }

}