package org.glcf3.gl.prg;

public interface PrograObject extends Cloneable {
    void bind();

    void unbind();

    int getId();
}
