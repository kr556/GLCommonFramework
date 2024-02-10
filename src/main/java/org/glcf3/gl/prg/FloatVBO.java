package org.glcf3.gl.prg;

import org.lwjgl.opengl.GL15;

import java.nio.Buffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class FloatVBO extends VBO.Float{
    protected FloatVBO(int id) {
        super(GL15.glGenBuffers());
    }
}