package org.glcf2.models;

import org.glcf2.*;
import org.glcf2.programobject.*;
import org.glcf2.programobject.attrib.Attribute;
import org.glcf2.programobject.attrib.AttributeArray;
import org.lwjgl.opengl.GLUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.glcf2.shaders.GLSL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class GLVBOModel extends Model<VBO> {
    private List<ProgramObjct> vbo = new ArrayList<>(Arrays.asList(new VBO[]{null, null, null})); // vertex, color, tex
    private IBO.Int indicies;
    private Visible points = Visible.POINTS_UNVISIBLE;
    private Visible lines = Visible.LINES_UNVISIBLE;
    private Visible surface = Visible.TRIANGLES;

    public GLVBOModel(VBO vertices, IBO.Int indices, Shader program) {
        this.vbo.set(VERTEX, vertices);
        this.indicies = indices;
        this.prg = program;
    }

    @Override
    public void drawing() {
        prg.bind();

        prg.getAttibs().forEach(Attribute::enable);

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

        if (points != Visible.POINTS_UNVISIBLE) glDrawElements(points.glName, indicies.length(), GL_UNSIGNED_INT, 0);
        if (lines != Visible.LINES_UNVISIBLE) glDrawElements(lines.glName, indicies.length(), GL_UNSIGNED_INT, 0);
        if (surface != Visible.SURFACE_UNVISIBLE) glDrawElements(surface.glName, indicies.length(), GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        prg.getAttibs().forEach(Attribute::enable);
    }

    public void isVisible(Visible... name) {
        for (Visible v : name) {
            switch (v) {
                case POINTS, POINTS_UNVISIBLE -> points = v;
                case LINES, LINE_LOOP, LINE_STRIP, LINES_UNVISIBLE -> lines = v;
                case TRIANGLES, POLYGON, QUAD_STRIP, QUADS, TRIANGLE_FAN, TRIANGLE_STRIP, SURFACE_UNVISIBLE ->
                        surface = v;
            }
        }
    }

    public IBO getIndicies() {
        return indicies;
    }

    public VBO[] getVBOs() {
        return (VBO[]) vbo.toArray(new ProgramObjct[0]);
    }

    public VBO getVBO(int index) {
        return (VBO) vbo.get(index);
    }

    @Override
    public void setColors(VBO color) {
        this.vbo.set(COLOR, color);
    }

    @Override
    public Texture getTexrure() {
        return (Texture) vbo.get(TEXTURE);
    }

    public void setIndicies(IBO.Int indicies) {
        this.indicies = (IBO.Int) indicies;
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

    @Override
    public GLVBOModel clone() {
        return (GLVBOModel) super.clone();
    }
}