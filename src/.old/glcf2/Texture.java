package org.glcf2;

import org.glcf2.programobject.VBO;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

//TODO クラスの抽象化
public abstract class Texture extends VBO {
    protected int width;
    protected int height;

    public abstract void bind();

    public abstract void bind(int samplerId);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
