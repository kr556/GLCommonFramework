package org.glcf3.gl.prg;

import org.lwjgl.opengl.GL15;

public class IntVBO extends VBO.Int {
    protected IntVBO(int id) {
        super(GL15.glGenBuffers());
    }
}
