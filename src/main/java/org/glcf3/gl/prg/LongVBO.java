package org.glcf3.gl.prg;

import org.lwjgl.opengl.GL15;

public class LongVBO extends VBO.Long {
    protected LongVBO(int id) {
        super(GL15.glGenBuffers());
    }
}
