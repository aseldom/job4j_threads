package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class FileOperate {

    public static synchronized String content(Predicate<Integer> filter, File file) {
        StringBuilder o = new StringBuilder();
        try (Reader i = new FileReader(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test(data)) {
                    o.append(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    public static synchronized void saveContent(String content, File file)  {
        try (Writer o = new FileWriter(file)) {
            o.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
