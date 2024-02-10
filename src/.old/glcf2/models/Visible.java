package org.glcf2.models;

import static org.lwjgl.opengl.GL11.*;

public enum Visible {
    POINTS_UNVISIBLE(-1),
    LINES_UNVISIBLE(-2),
    SURFACE_UNVISIBLE(-3),
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

    Visible(int name) {
        glName = name;
    }

    final int glName;
}
