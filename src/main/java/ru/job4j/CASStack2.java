package ru.job4j;


import java.util.concurrent.atomic.AtomicReference;

public class CASStack2<T> {
    AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> ref;
        do {
            ref = head.get();
            newNode.next = ref;
        } while (!head.compareAndSet(ref, newNode));
    }

    public T pop() {
        Node<T> ref;
        Node<T> temp;
        do {
            ref = head.get();
            if (ref == null) {
                throw new IllegalArgumentException("No args");
            }
            temp = ref.next;
        } while (!head.compareAndSet(ref, temp));
//        ref.next = null;
        return ref.value;
    }

    static class Node<T> {
        T value;

        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}

