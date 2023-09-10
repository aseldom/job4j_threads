package ru.job4j.io;

import java.io.File;
import java.util.function.IntPredicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File f) {
        file = f;
    }

    public void save(String content) {
        SaveFile.saveContent(content, this.file);
    }

    public String getContent() {
        return content(i -> true, file);
    }

    public String getContentWithoutUnicode() {
        return content(i -> i < 0x80, file);
    }

    private synchronized String content(IntPredicate filter, File file) {
        StringBuilder o = new StringBuilder();
        String i = LoadFile.load(file);
        i.chars().filter(filter).forEach(o::append);
        return o.toString();
    }
}