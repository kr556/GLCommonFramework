package org.glcf3.gl.prg;

import org.lwjgl.opengl.GL15;

public class DoubleVBO extends VBO.Double {
    protected DoubleVBO(int id) {
        super(GL15.glGenBuffers());
    }
}
