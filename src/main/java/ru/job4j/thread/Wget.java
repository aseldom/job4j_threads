package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream("tmp.xml")) {
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1
                    && !Thread.currentThread().isInterrupted()) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                var delta = System.nanoTime() - downloadAt;
                int time = (int) (512000000 / speed - delta);
                System.out.println(delta + " - " + time);
                Thread.sleep(0, Math.max(time, 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static boolean validate(String url, int speed) {
        String urlRegex =
                "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))"
                        + "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)"
                        + "([).!';/?:,][[:blank:]])?$";
        Pattern urlPattern = Pattern.compile(urlRegex);
        return urlPattern.matcher(url).matches() && speed > 0;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (validate(url, speed)) {
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        } else {
            System.out.println("Введены неверные параметры");
        }
    }
}