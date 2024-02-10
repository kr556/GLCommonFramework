package org.glcf3.gl.prg;

import static org.lwjgl.opengl.GL15.*;

public class IBO implements PrograObject {
    private int length;
    private final int id;

    public IBO() {
        id = glGenBuffers();
    }

    public int size() {
        return length;
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public int getId() {
        return id;
    }

    public void register(int[] indiceis) {
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiceis, GL_STATIC_DRAW);
        unbind();
    }
}
