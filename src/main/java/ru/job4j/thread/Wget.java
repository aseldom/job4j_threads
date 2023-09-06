package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private static final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))"
                    + "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)"
                    + "([).!';/?:,][[:blank:]])?$";
    private final String url;
    private final String path;
    private final int speed;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }

    @Override
    public void run() {
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(path)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            long bytesAll = 0;
            var downloadAt = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1
                    && !Thread.currentThread().isInterrupted()) {
                out.write(dataBuffer, 0, bytesRead);
                bytesAll += bytesRead;
                if (bytesAll > speed) {
                    bytesAll = 0;
                    long delta = (1000000000 - (System.nanoTime() - downloadAt)) / 1000000;
                    if (delta > 0) {
                        Thread.sleep((int) delta);
                    }
                    downloadAt = System.nanoTime();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Number of parameters should be 3");
        }
        if (!args[0].matches(URL_REGEX)) {
            throw new IllegalArgumentException("The first argument is not a web address");
        }
        if (Integer.parseInt(args[1]) <= 0)  {
            throw new IllegalArgumentException("The second argument must be a positive number");
        }
        Paths.get(args[2]);
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String  file = args[2];
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
    }
}