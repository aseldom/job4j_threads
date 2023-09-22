package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    public void testSerial3to3Matrix() {
        RolColSum.Sums[] res = RolColSum.sum(new int[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        int[][] expected = new int[][]{{6, 3}, {6, 6}, {6, 9}};
        assertThat(Arrays.stream(res)
                .map(x -> new int[]{x.getRowSum(), x.getColSum()})
                .toArray())
                .isEqualTo(expected);
    }

    @Test
    public void testSerial4to4Matrix() {
        RolColSum.Sums[] res = RolColSum.sum(new int[][]{{4, 2, 0, 3}, {5, 1, 3, 2}, {3, 2, 1, 4}, {0, 2, 1, 3}});
        int[][] expected = new int[][]{{9, 12}, {11, 7}, {10, 5}, {6, 12}};
        assertThat(Arrays.stream(res)
                .map(x -> new int[]{x.getRowSum(), x.getColSum()})
                .toArray())
                .isEqualTo(expected);
    }

    @Test
    public void testAsync3to3Matrix() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] res = RolColSum.asyncSum(new int[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        int[][] expected = new int[][]{{6, 3}, {6, 6}, {6, 9}};
        assertThat(Arrays.stream(res)
                .map(x -> new int[]{x.getRowSum(), x.getColSum()})
                .toArray())
                .isEqualTo(expected);
    }

    @Test
    public void testAsync4to4Matrix() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] res = RolColSum.asyncSum(new int[][]{{4, 2, 0, 3}, {5, 1, 3, 2}, {3, 2, 1, 4}, {0, 2, 1, 3}});
        int[][] expected = new int[][]{{9, 12}, {11, 7}, {10, 5}, {6, 12}};
        assertThat(Arrays.stream(res)
                .map(x -> new int[]{x.getRowSum(), x.getColSum()})
                .toArray())
                .isEqualTo(expected);
    }
}