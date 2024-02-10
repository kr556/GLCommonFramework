package org.glcf3;

import org.glcf3.gl.Vertex;

public abstract class Model implements Drawing {
    public abstract Shader getShader();

    public abstract Vertex[] getVerticies();

    public abstract void setShader(Shader shader);
}
