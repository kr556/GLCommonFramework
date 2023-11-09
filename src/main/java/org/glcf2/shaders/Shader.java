package org.glcf2.shaders;

import org.glcf2.ShaderBase;
import org.glcf2.globject.AttribObject;
import org.glcf2.io.ShaderIO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class Shader extends ShaderBase {
    private int program;
    private int vertexId;
    private int fragmentId;
    private List<AttribObject> attribs = new ArrayList<>();

    public static Shader readFile(String filaName) {
        return new Shader(
                ShaderIO.readVertex(new File("shader/glsl/" + filaName), e -> {
                    System.err.println("vertex file not found: shader/glsl/" + filaName);
                    return null;
                }),
                ShaderIO.readFragment(new File("shader/glsl/" + filaName), e -> {
                    System.err.println("fragment file not found: shader/glsl/" + filaName);
                    return null;
                }));
    }

    public Shader(String vSrc, String fSrc) {
        create(vSrc, fSrc);
        compile();
    }

    @Override
    protected void create(String vSrc, String fSrc) {
        this.program = glCreateProgram();

        this.vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vSrc);

        this.fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fSrc);
    }

    @Override
    public void compile() {
        glCompileShader(vertexId);
        if (glGetShaderi(vertexId, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(vertexId));
            System.exit(101);
        }

        glCompileShader(fragmentId);
        if (glGetShaderi(fragmentId, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(fragmentId));
            System.exit(102);
        }

        glAttachShader(program, vertexId);
        glAttachShader(program, fragmentId);

        attribs.add(new AttribObject(0, 3, GL_FLOAT, false, 0));
        attribs.add(new AttribObject(1, 4, GL_FLOAT, false, 0));
        attribs.add(new AttribObject(2, 4, GL_FLOAT, false, 0));

        attribs.get(0).bindProgram(program, AttribObject.VERTICES);
        attribs.get(1).bindProgram(program, AttribObject.COLOR);
        attribs.get(2).bindProgram(program, AttribObject.TEXTURES);

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(103);
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != GL_TRUE) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(104);
        }
    }

    @Override
    public void bind() {
        glUseProgram(program);
    }

    @Override
    public int getProgram() {
        return program;
    }

    @Override
    public int getVertexId() {
        return vertexId;
    }

    @Override
    public int getFragmentId() {
        return fragmentId;
    }

    @Override
    public List<AttribObject> getAttibs() {
        return attribs;
    }
}
