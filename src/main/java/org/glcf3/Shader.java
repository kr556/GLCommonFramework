package org.glcf3;

import org.glcf3.gl.prg.GLAttribute;
import org.glcf3.gl.prg.PrograObject;
import org.linear.main.matrix.Matrix;
import org.linear.main.vector.FloatVector;
import org.linear.main.vector.IntVector;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public interface Shader extends Cloneable {
    void compile();

    void bind();

    int getProgram();

    int getVertexId();

    int getFragmentId();

    List<GLAttribute> getAttibs();

    boolean isComliled();

    default void setUniform(String valueName, int value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            glUniform1i(location, value);
        }
    }

    default void setUniform(String valueName, float value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            glUniform1f(location, value);
        }
    }

    default void setUniformv(String valueName, int... value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            switch (value.length) {
                case 1 -> glUniform1iv(location, value);
                case 2 -> glUniform2iv(location, value);
                case 3 -> glUniform3iv(location, value);
                case 4 -> glUniform4iv(location, value);
            }
        }
    }

    default void setUniformv(String valueName, float... value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            switch (value.length) {
                case 1 -> glUniform1fv(location, value);
                case 2 -> glUniform2fv(location, value);
                case 3 -> glUniform3fv(location, value);
                case 4 -> glUniform4fv(location, value);
            }
        }
    }

    default void setUniformvf(String valueName, FloatVector<?> value) {
        int location = glGetUniformLocation(getProgram(), valueName);
        float[] vec = value.toNewArray();

        if (location != -1) {
            switch (value.dimension()) {
                case 1 -> glUniform1fv(location, vec);
                case 2 -> glUniform2fv(location, vec);
                case 3 -> glUniform3fv(location, vec);
                case 4 -> glUniform4fv(location, vec);
            }
        }
    }

    default void setUniformvi(String valueName, IntVector<?> value) {
        int location = glGetUniformLocation(getProgram(), valueName);
        int[] vec = value.toNewArray();

        if (location != -1) {
            switch (value.dimension()) {
                case 1 -> glUniform1iv(location, vec);
                case 2 -> glUniform2iv(location, vec);
                case 3 -> glUniform3iv(location, vec);
                case 4 -> glUniform4iv(location, vec);
            }
        }
    }

    default void setUniformm(String valueName, Matrix<Float, ?, ?> value) {
        int location = glGetUniformLocation(getProgram(), valueName);
        FloatBuffer mat = BufferUtils.createFloatBuffer(value.elementsSize());
        value.get(mat);

        if (location != -1) {
            switch (value.elementsSize()) {
                case 1 -> setUniformm(valueName, value.get(0));
                case 4 -> glUniformMatrix2fv(location, false, mat);
                case 9 -> glUniformMatrix3fv(location, false, mat);
                case 16 -> glUniformMatrix4fv(location, false, mat);
            }
        }
    }

    default void setUniformm(String valueName, float... value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            switch (value.length) {
                case 4 -> glUniformMatrix2fv(location, false, value);
                case 9 -> glUniformMatrix3fv(location, false, value);
                case 16 -> glUniformMatrix4fv(location, false, value);
            }
        }
    }

    default void setUniformSampler(String valueName, Texture sampler) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            glUniform1i(location, 0);
            sampler.bind();
        }
    }

    Shader clone();
}
