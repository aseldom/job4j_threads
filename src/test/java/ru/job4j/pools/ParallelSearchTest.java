package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void linearStringSearchThen4() {
        String[] array = {"d", "a", "a", "b", "c", "g"};
        int expected = 4;
        int res = ParallelSearch.search(array, "c");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void linearStringSearchThenNotFinding() {
        String[] array = {"d", "a", "a", "b", "g", "g"};
        int expected = -1;
        int res = ParallelSearch.search(array, "c");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void parallelStringSearchThen11() {
        String[] array = {"we", "d", "a", "a", "b", "c", "g", "ee", "ww", "ww", "gs", "get"};
        int expected = 11;
        int res = ParallelSearch.search(array, "get");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void parallelIntSearchThen3() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int expected = 3;
        int res = ParallelSearch.search(array, 4);
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void parallelIntSearchThenNotFinding() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int expected = -1;
        int res = ParallelSearch.search(array, 99);
        assertThat(res).isEqualTo(expected);
    }

}