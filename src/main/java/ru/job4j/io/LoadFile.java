package ru.job4j.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoadFile {

    public synchronized static String load(File file) {
        String o = "";
        try (BufferedReader i = new BufferedReader(new FileReader(file))) {
            o = i.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
}
