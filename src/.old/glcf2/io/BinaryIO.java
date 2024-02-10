package org.glcf2.io;

import java.io.*;

public final class BinaryIO {
    private BinaryIO() {}

    public static byte[] read(String fileName) throws IOException {
        InputStream in = ResourceReader.read(fileName);
        byte[] re = in.readAllBytes();
        in.close();
        return re;
    }

    public static byte[] read(File fileName) throws IOException {
        InputStream in = new FileInputStream(fileName);
        byte[] re = in.readAllBytes();
        in.close();
        return re;
    }

    public static byte[] read(String fileName, ExceptionCatcher<byte[]> ec) {
        try {
            return read(fileName);
        } catch (IOException e) {
            return ec.invoke(e);
        }
    }

    public static byte[] read(File fileName, ExceptionCatcher<byte[]> ec) {
        try {
            return read(fileName);
        } catch (IOException e) {
            return ec.invoke(e);
        }
    }

    public static void write(String fileName,byte[] bins) throws IOException {
        OutputStream os = new FileOutputStream(fileName);
        os.write(bins);
        os.close();
    }

    public static void write(File fileName, byte[] bins) throws IOException {
        OutputStream os = new FileOutputStream(fileName);
        os.write(bins);
        os.close();
    }

    public static void write(String fileName, byte[] bins, ExceptionCatcher<Void> ec) {
        try {
            write(fileName, bins);
        } catch (IOException e) {
            ec.invoke(e);
        }
    }

    public static void write(File fileName, byte[] bins, ExceptionCatcher<Void> ec) {
        try {
            write(fileName, bins);
        } catch (IOException e) {
            ec.invoke(e);
        }
    }
}
