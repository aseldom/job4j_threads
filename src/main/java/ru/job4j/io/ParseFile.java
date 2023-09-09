package ru.job4j.io;

import java.io.*;

public class ParseFile {

    private final File file;

    public ParseFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }
}