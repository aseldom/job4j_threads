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
        int res;
        if (from == to) {
            if (object.equals(array[from])) {
                return from;
            } else {
                return -1;
            }
        }
        int mid = (to + from) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, object, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, object, mid + 1, to);
        res = leftSearch.fork().invoke();
        if (res != -1) {
            return res;
        }
        res = rightSearch.fork().invoke();
        return res;
    }

    public int search() {
        int res;
        if (array.length <= 10) {
            res = linearSearch();
        } else {
            ForkJoinPool fp = new ForkJoinPool();
            res = fp.invoke(this);
        }
        return res;
    }

    private int linearSearch() {
        int res = -1;
        for (int i = 0; i < array.length; i++) {
            if (object.equals(array[i])) {
                res = i;
                break;
            }
        }
        return res;
    }
}