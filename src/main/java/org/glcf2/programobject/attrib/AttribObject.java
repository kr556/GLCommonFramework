package org.glcf2.programobject.attrib;

import org.glcf2.programobject.ProgramObjct;

import static org.lwjgl.opengl.GL11C.GL_INT;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;

// TODO: 2023/12/15 サブクラス共の実装
public abstract class AttribObject {
    private int type;
    private boolean normalized;
    private int stride;
    private int id;

    public AttribObject(int id, int type, boolean normalized, int stride) {
        this.id = id;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;
    }

    public abstract int size();

    public abstract void enable();

    public abstract void bindProgram(int program, String name);

    public abstract void bind(ProgramObjct buffer);

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getStride() {
        return stride;
    }

    public boolean isNormalized() {
        return normalized;
    }
}
