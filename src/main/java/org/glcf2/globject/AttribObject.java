package org.glcf2.globject;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;

public final class AttribObject extends GLObjct {
    public static String VERTICES = "vertices";
    public static String TEXTURES = "textures";
    public static String COLOR = "color";

//    private static final List<Integer> attribs = new ArrayList<>();
    private int id;
    private int size;
    private int type;
    private boolean normalized;
    private int stride;

    public AttribObject(int id, int size, int type, boolean normalized, int stride) {
        this.id = id;
        this.size = size;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;

//        attribs.add(this.id);
    }

    public void bind(GLObjct buffer) {
        glBindBuffer(GL_ARRAY_BUFFER, buffer.id());
        glVertexAttribPointer(id, size, type, normalized, stride, 0);
    }

    public void bind(int size, int type, boolean normalized, int stride, GLObjct buffer) {
        glBindBuffer(GL_ARRAY_BUFFER, buffer.id());
        glVertexAttribPointer(id, size, type, normalized, stride, buffer.id());
    }

    public void bindProgram(int program, String name) {
        glBindAttribLocation(program, id, name);
    }

    public int id() {
        return id;
    }

    @Override
    public void bind() {
        glEnableVertexAttribArray(id);
    }

    @Override
    protected void finalize() {
//        synchronized (attribs) {
//            attribs.remove((Object)id);
//        }
    }

    public int getSize() {
        return size;
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

    @Override
    public String toString() {
        return "AttribObject{" +
                "id=" + id +
                ", size=" + size +
                ", type=" + type +
                ", normalized=" + normalized +
                ", stride=" + stride +
                '}';
    }
}
