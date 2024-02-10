package org.glcf2.io;

import java.io.*;

public final class TextIO {
    private TextIO() {}

    public static String read(String fileName) throws IOException {
        StringBuilder txt = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(ResourceReader.read(fileName)));
        String line;

        while ((line = br.readLine()) != null) {
            txt.append(line);
            txt.append("\n");
        }

        br.close();

        return txt.toString();
    }

    public static String read(String fileName, ExceptionCatcher<String> ec) {
        try {
            return read(fileName);
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }

    public static String read(File fileName) throws IOException {
        StringBuilder code = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            code.append(line);
            code.append("\n");
        }

        br.close();

        return code.toString();
    }

    public static String read(File fileName, ExceptionCatcher<String> ec) {
        try {
            return read(fileName);
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }

    public static Closeable write(String path, String text) throws IOException {
        FileWriter f = new FileWriter(path);
        f.write(text);
        f.close();
        return f;
    }

    public static Closeable write(File path, String text) throws IOException {
        FileWriter f = new FileWriter(path);
        f.write(text);
        f.close();
        return f;
    }

    public static void write(String path, String text, ExceptionCatcher<Void> ec) {
        try {
            write(path, text);
        } catch (IOException e) {
            ec.invoke(e);
        }
    }

    public static void write(File path, String text, ExceptionCatcher<Void> ec) {
        try {
            write(path, text);
        } catch (IOException e) {
            ec.invoke(e);
        }
    }
}
