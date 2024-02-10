package org.glcf2.io;

import java.io.InputStream;

public class ResourceReader {
    private ResourceReader() {}

    public static InputStream read(String fileName) {
        return ResourceReader.class.getClassLoader().getResourceAsStream(fileName);
    }
}
