package org.glcf3.gl;

import org.glcf3.Texture;
import org.glcf3.gl.prg.VBO;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class GLTexture extends VBO implements Texture {
    public static GLTexture read(File fileName) throws IOException {
        return new GLTexture(ImageIO.read(fileName));
    }

    public static GLTexture read(String resourcesTree) throws IOException {
        return new GLTexture(ImageIO.read(GLTexture.class.getResourceAsStream(resourcesTree)));
    }

    private int width;
    private int height;
    private BufferedImage origin;

    public GLTexture(BufferedImage image) {
        super(glGenTextures());
        this.origin = image;

        width = image.getWidth();
        height = image.getHeight();

        int[] pixsRow = image.getRGB(0, 0, width, height, null, 0, width);

        ByteBuffer pixs = BufferUtils.createByteBuffer(pixsRow.length * 4);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pix = pixsRow[y * width + x];
                pixs.put((byte) ((pix >> 16) & 0xFF));    // red
                pixs.put((byte) ((pix >> 8) & 0xFF));     // green
                pixs.put((byte) (pix & 0xFF));            // blue
                pixs.put((byte) ((pix >> 24) & 0xFF));    // alphe
            }
        }
        pixs.flip();

        bind();

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixs);

        unbind();
    }

    @Override
    public void bind(int samplerId) {
        if (samplerId >= 0 && samplerId <= 31) {
            glActiveTexture(GL_TEXTURE0 + samplerId);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Class<?> getElementType() {
        return Byte.class;
    }

    @Override
    public BufferedImage getImage() {
        return origin;
    }
}

