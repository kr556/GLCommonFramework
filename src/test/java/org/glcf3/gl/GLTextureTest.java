package org.glcf3.gl;

import org.glcf3.Window;
import org.glcf3.gl.gui.GLWindow;
import org.junit.jupiter.api.Test;
import org.linear.main.vector.Vector4i;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.opengl.GL11.*;

class GLTextureTest extends GLTexture{
    public GLTextureTest(BufferedImage image) {
        super(image);
    }

    public static void main(String[] args) throws IOException {
        new GLWindow(new Vector4i(100, 100, 100, 100), "title");
    }
}