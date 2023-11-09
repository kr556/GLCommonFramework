package org.glcf2.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
}
