package org.glcf2.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageReader {
    private ImageReader() {}

    public static BufferedImage read(File fileName) throws ImageNotFoundException {
        BufferedImage re;
        try {
            re = ImageIO.read(fileName);
        } catch (IOException e) {
            throw new ImageNotFoundException();
        }
        return re;
    }

    public static BufferedImage read(String fileName) throws ImageNotFoundException {
        BufferedImage re;
        InputStream ip = ImageReader.class.getClassLoader().getResourceAsStream(fileName);
        try {
            re = ImageIO.read(ip);
        } catch (IOException e) {
            throw new ImageNotFoundException();
        }
        return re;
    }

    public static BufferedImage read(String fileName, ExceptionCatcher exceptionCatcher) {
        try {
            return read(fileName);
        } catch (ImageNotFoundException e) {
            exceptionCatcher.invoke(e);
        }
        return null;
    }

    public static BufferedImage read(File fileName, ExceptionCatcher exceptionCatcher) {
        try {
            return read(fileName);
        } catch (ImageNotFoundException e) {
            exceptionCatcher.invoke(e);
        }
        return null;
    }
}
