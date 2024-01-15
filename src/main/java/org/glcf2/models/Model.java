package org.glcf2.models;

import org.glcf2.Drawing;
import org.glcf2.Shader;
import org.glcf2.Texture;
import org.glcf2.programobject.VBO;
import org.glcf2.vertex.ArrayModel;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector4f;

public abstract class Model implements Drawing, Cloneable {
    protected Shader prg;
    private ArrayModel<Matrix4f, Vector4f> model;

    @Override
    public void setShader(Shader shader) {
        this.prg = shader;
    }

    @Override
    public Shader getShader() {
        return prg;
    }

    public void setPrgogram(Shader prg) {
        this.prg = prg;
    }

    public abstract void setVerties(VBO verties);

    public abstract void setTexture(Texture tex);

    public abstract void setColor(VBO colors);

    //TODO フィールドのディープコピー化
    @Override
    public Model clone() {
        try {
            Model re = (Model) super.clone();
            return re;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone model.");
        }
    }
}
