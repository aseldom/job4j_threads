package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("First thread starts")
        );
        Thread second = new Thread(
                () -> System.out.println("Second thread starts")
        );
        System.out.println(first.getName());
        System.out.println(second.getName());
        first.start();
        second.start();
         do {
            System.out.println(first.getState());
            System.out.println(second.getState());
        } while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED);
        System.out.println("Work ends");
    }
}