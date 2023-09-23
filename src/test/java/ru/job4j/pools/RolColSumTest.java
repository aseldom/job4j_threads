package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    public void testSerial3to3Matrix() {
        Sums[] res = RolColSum.sum(new int[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        Sums[] expected = new Sums[]{
                new Sums(6, 3),
                new Sums(6, 6),
                new Sums(6, 9)
        };
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void testSerial4to4Matrix() {
        Sums[] res = RolColSum.sum(new int[][]{{4, 2, 0, 3}, {5, 1, 3, 2}, {3, 2, 1, 4}, {0, 2, 1, 3}});
        Sums[] expected = new Sums[]{
                new Sums(9, 12),
                new Sums(11, 7),
                new Sums(10, 5),
                new Sums(6, 12)
        };
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void testAsync3to3Matrix() throws ExecutionException, InterruptedException {
        Sums[] res = RolColSum.asyncSum(new int[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        Sums[] expected = new Sums[]{
                new Sums(6, 3),
                new Sums(6, 6),
                new Sums(6, 9)
        };
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void testAsync4to4Matrix() throws ExecutionException, InterruptedException {
        Sums[] res = RolColSum.asyncSum(new int[][]{{4, 2, 0, 3}, {5, 1, 3, 2}, {3, 2, 1, 4}, {0, 2, 1, 3}});
        Sums[] expected = new Sums[]{
                new Sums(9, 12),
                new Sums(11, 7),
                new Sums(10, 5),
                new Sums(6, 12)
        };
        assertThat(res).isEqualTo(expected);
    }
}