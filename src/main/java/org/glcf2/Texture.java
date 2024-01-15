package org.glcf2;

import org.glcf2.programobject.VBO;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

//TODO クラスの抽象化
public class Texture extends VBO {
    private int width;
    private int height;

    public Texture(BufferedImage image) {
        this.id = glGenTextures();

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

        this.id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixs);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void bind(int samplerId) {
        if (samplerId >= 0 && samplerId <= 31) {
            glActiveTexture(GL_TEXTURE0 + samplerId);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }
}
