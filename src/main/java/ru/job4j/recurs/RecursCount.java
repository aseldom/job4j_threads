package ru.job4j.recurs;

import java.util.Arrays;

public class RecursCount {

    private static int count(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        return 1 + count(Arrays.copyOfRange(arr, 1, arr.length));
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 4, 5};
        System.out.println(count(arr));

    }
}
