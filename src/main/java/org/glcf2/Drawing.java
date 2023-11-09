package org.glcf2;

public interface Drawing<R/*This type*/> {
    R create();
    void drawing();

    void setShader(ShaderBase shader);
    void setModel(ArrayModel model);
}
