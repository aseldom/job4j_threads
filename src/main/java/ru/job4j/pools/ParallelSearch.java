package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T object, int from, int to) {
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return linearSearch();
        }
        int mid = (to + from) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, object, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, object, mid + 1, to);
        return Math.max(leftSearch.fork().invoke(), rightSearch.fork().invoke());
    }

    private int linearSearch() {
        int res = -1;
        for (int i = from; i < to; i++) {
            if (object.equals(array[i])) {
                res = i;
                break;
            }
        }
        return res;
    }

    public static <T> int search(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, object, 0, array.length));
    }
}