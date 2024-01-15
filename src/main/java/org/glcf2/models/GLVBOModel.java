package org.glcf2.models;

import org.glcf2.*;
import org.glcf2.programobject.*;
import org.glcf2.programobject.attrib.AttribObjectArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.glcf2.shaders.GLSL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class GLVBOModel extends Model {
    private List<VBO> vbo = new ArrayList<>(Arrays.asList(new VBO[]{null, null, null})); // vertex, color, tex
    private IBO indicies;

    public GLVBOModel(VBO vertices, IBO.Int indices, Shader program) {
        this.vbo.set(VERTEX, vertices);
        this.indicies = indices;
        this.prg = program;
    }

    @Override
    public void drawing() {
        prg.bind();

        prg.getAttibs().forEach(AttribObjectArray::enable);

        for (int i = 0; i < prg.getAttibs().size(); i++) {
            if (i < vbo.size()) {
                ProgramObjct glo = vbo.get(i);

                if (glo != null) {
                    glo.bind();
                    prg.getAttibs().get(i).bind(glo);
                }
            } else {
                break;
            }
        }

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicies.id());
        glDrawElements(GL_TRIANGLES, indicies.length(), GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        prg.getAttibs().forEach(AttribObjectArray::enable);
    }

    public IBO getIndicies() {
        return indicies;
    }

    public VBO[] getVBOs() {
        return vbo.toArray(new VBO[0]);
    }

    public VBO getVBO(int index) {
        return vbo.get(index);
    }

    @Override
    public void setColor(VBO color) {
        this.vbo.set(COLOR, color);
    }

    public void setIndicies(IBO indicies) {
        this.indicies = indicies;
    }

    public void setVerties(VBO verties) {
        this.vbo.set(VERTEX, verties);
    }

    public void addVBO(VBO vbo) {
        this.vbo.add(vbo);
    }

    @Override
    public void setTexture(Texture tex) {
        this.vbo.set(TEXTURE, tex);
    }
}