package org.glcf2.models;

import org.glcf2.Texture;
import org.glcf2.programobject.ProgramObjct;
import org.glcf2.programobject.VBO;
import org.glcf2.vertex.ArrayModel;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector4f;

/**
 * vboを使用せずに描画します。
 */
public class GLRawModel extends Model{
    private ArrayModel<Matrix4f, Vector4f> arrayModel;

    public GLRawModel() {}

    @Override
    public void drawing() {

    }

    @Override
    public void setVerties(VBO verties) {

    }

    @Override
    public void setTexture(Texture tex) {

    }

    @Override
    public void setColor(ProgramObjct colors) {

    }

    @Override
    public void setDrawMode(int mode) {

    }
}
