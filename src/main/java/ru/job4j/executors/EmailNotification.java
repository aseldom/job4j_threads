package ru.job4j.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s", user.getName(), email);
        String body  = String.format("Add a new event to %s", user.getName());
        send(subject, body, email);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }

    public static void main(String[] args) {
        EmailNotification notification = new EmailNotification();
        notification.pool.submit(() -> notification.emailTo(new User("Vasya Pupkin", "abc@mail.com")));
        notification.close();
    }
}
