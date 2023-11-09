package org.glcf2.models;

import org.glcf2.*;
import org.glcf2.globject.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Model extends GLObjct implements BufferdModel, Drawing<Model> {
    private static final List<Integer> ids = new ArrayList<>();

    private int id;
    private ShaderBase prg;
    private List<VertexBufferObject> globj = new ArrayList<>(Arrays.asList(new VertexBufferObject[]{null, null, null})); // vertex, color, tex
    private IndexBufferObject indicies;

    @Override
    public Model create() {
        Model re = new Model();
        re.id = GLUtils.createId(ids);
        ids.add(this.id);
        return re;
    }

    @Override
    public void drawing() {
        prg.bind();

        prg.getAttibs().forEach(AttribObject::bind);

        for (int i = 0; i < prg.getAttibs().size(); i++) {
            if (i < globj.size()) {
                GLObjct glo = globj.get(i);

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

        prg.getAttibs().forEach(AttribObject::bind);
    }

    @Override
    public void setShader(ShaderBase shader) {
        this.prg = shader;
    }

    @Override
    public void setModel(ArrayModel model) {
        // TODO: 2023/11/03 未実装
    }

    public void setTexture(Texture tex) {
        this.globj.set(2, tex);
    }

    public void setColor(VertexBufferObject color) {
        this.globj.set(1, color);
    }

    public void setIndicies(IndexBufferObject indicies) {
        this.indicies = indicies;
    }

    public void setPrgogram(ShaderBase prg) {
        this.prg = prg;
    }

    public void setVerties(VertexBufferObject verties) {
        this.globj.set(0, verties);
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void bind() {}

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() {
        synchronized (ids) {
            ids.remove((Object)id);
        }
    }
}
