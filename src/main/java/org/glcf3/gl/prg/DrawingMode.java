package org.glcf3.gl.prg;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_POLYGON;

public enum DrawingMode {
    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    LINE_STRIP(GL_LINE_STRIP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN),
    QUADS(GL_QUADS),
    QUAD_STRIP(GL_QUAD_STRIP),
    POLYGON(GL_POLYGON);

    private final int id;

    DrawingMode(int glId) {
        this.id = glId;
    }

    public int glId() {
        return id;
    }
}
