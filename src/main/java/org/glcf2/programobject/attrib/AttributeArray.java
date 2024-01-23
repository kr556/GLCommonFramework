package org.glcf2.programobject.attrib;

import org.glcf2.programobject.ProgramObjct;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;

public final class AttributeArray extends Attribute {
    public static String VERTICES = "vertices";
    public static String TEXTURES = "textures";
    public static String COLORS = "color";

    private int size;

    public AttributeArray(int id, int size, int type, boolean normalized, int stride) {
        super(id, type, normalized, stride);
        this.size = size;
    }

    @Override
    public void bindProgram(int program, String name) {
        glBindAttribLocation(program, getId(), name);
    }

    @Override
    public void bind(ProgramObjct buffer) {
        glBindBuffer(GL_ARRAY_BUFFER, buffer.id());
        glVertexAttribPointer(getId(), size, getType(), isNormalized(), getStride(), 0);
    }

    @Override
    public void enable() {
        glEnableVertexAttribArray(getId());
    }

    @Override
    public int size() {
        return size;
    }
}
