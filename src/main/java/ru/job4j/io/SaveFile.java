package ru.job4j.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {

    public static synchronized void saveContent(String content, File file)  {
        try (BufferedWriter o = new BufferedWriter(new FileWriter(file))) {
            o.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
