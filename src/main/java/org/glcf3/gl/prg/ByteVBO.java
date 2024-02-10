package org.glcf3.gl.prg;

import org.lwjgl.opengl.GL15;

public class ByteVBO extends VBO.Byte {
    public ByteVBO() {
        super(GL15.glGenBuffers());
    }
}
