package org.glcf2.io;

import java.io.File;
import java.io.IOException;

public final class ShaderReader {
    private static String vertex = ".vert";
    private static String fragment = ".frag";

    private ShaderReader() {}

    public static String readFragment(String fileName) throws IOException {
        if (!fileName.substring(fragment.length()).equals(fragment)) fileName += fragment;
        return TextIO.read(fileName);
    }

    public static String readFragment(String fileName, ExceptionCatcher<String> ec) {
        try {
            return readFragment(fileName);
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }

    public static String readFragment(File fileName) throws IOException {
        if (!fileName.getPath().substring(3).equals(fragment)) fileName = new File("shader\\glsl\\" + fileName.getPath() + fragment);
        System.out.println(fileName);

        return TextIO.read(fileName);
    }

    public static String readFragment(File fileName, ExceptionCatcher<String> ec) {
        try {
            return readFragment(fileName);
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }

    public static String readVertex(String fileName) throws IOException {
        if (!fileName.substring(vertex.length()).equals(vertex)) fileName += vertex;

        return TextIO.read(fileName);
    }

    public static String readVertex(String fileName, ExceptionCatcher<String> ec) {
        if (!fileName.substring(vertex.length()).equals(vertex)) fileName += vertex;

        try {
            return TextIO.read(fileName);
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }

    public static String readVertex(File fileName) throws IOException {
        if (!fileName.getPath().substring(3).equals(vertex)) fileName = new File("shader\\glsl\\" + fileName.getPath() + vertex);

        return TextIO.read(fileName);
    }

    public static String readVertex(File fileName, ExceptionCatcher<String> ec) {
        try {
            return readVertex(fileName);
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }
}
