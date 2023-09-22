package ru.job4j.pools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    private static final Logger LOG = LoggerFactory.getLogger(RolColSum.class.getName());

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            int columnSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                columnSum += matrix[j][i];
            }
           sums[i] = new Sums();
           sums[i].setRowSum(rowSum);
           sums[i].setColSum(columnSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        CompletableFuture<Sums>[] futures = new CompletableFuture[matrix.length];
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            futures[i] = sumRowColumn(i, matrix);
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = futures[i].get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> sumRowColumn(int i, int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {
            int size = matrix.length;
            int rowSum = 0;
            int columnSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                columnSum += matrix[j][i];
            }
            Sums sums = new Sums();
            sums.setRowSum(rowSum);
            sums.setColSum(columnSum);
            LOG.info("Current thread - {}", Thread.currentThread().getName());
            return sums;
        });
    }
}