package org.glcf2;

import org.glcf2.globject.AttribObject;
import org.liner.main.vector.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniform1i;

public abstract class ShaderBase {
    protected abstract void create(String vSrc, String fSrc);

    public abstract void compile();

    public abstract void bind();

    public abstract int getProgram();

    public abstract int getVertexId();

    public abstract int getFragmentId();

    public abstract List<AttribObject> getAttibs();

    public void setUniform(String valueName, int value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            glUniform1i(location, value);
        }
    }

    public void setUniform(String valueName, float value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            glUniform1f(location, value);
        }
    }

    public void setUniformv(String valueName, int... value) {
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

    public void setUniformm(String valueName, float... value) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            switch (value.length) {
                case 4 -> glUniformMatrix2fv(location, false, value);
                case 9 -> glUniformMatrix3fv(location, false, value);
                case 16 -> glUniformMatrix4fv(location, false, value);
            }
        }
    }

    public void setUniformv(String valueName, float... value) {
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

    public void setUniformm(String valueName, Vector4f value) {
        int location = glGetUniformLocation(getProgram(), valueName);
        FloatBuffer mat = BufferUtils.createFloatBuffer(16);
        value.get(mat);

        if (location != -1) {
            glUniformMatrix4fv(location, false, mat.array());
        }
    }

    public void setUniformv(String valueName, Texture sampler) {
        int location = glGetUniformLocation(getProgram(), valueName);

        if (location != -1) {
            glUniform1i(location, 0);
            sampler.bind();
        }
    }
}
