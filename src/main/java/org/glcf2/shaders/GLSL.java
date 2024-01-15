package org.glcf2.shaders;

import org.glcf2.Shader;
import org.glcf2.programobject.attrib.AttribObjectArray;
import org.glcf2.io.ShaderIO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class GLSL extends Shader {
    public static final int
            VERTEX = 0,
            COLOR = 1,
            TEXTURE = 2;

    private int program;
    private int vertexId;
    private int fragmentId;
    private List<AttribObjectArray> attribs = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    /**
     * 属性追加後に{@link GLSL#compile()}でコンパイルする必要があります。
     * @param filaName file name
     */
    public static GLSL readFileCostumAttribute(File filaName) {
        return new GLSL(
                ShaderIO.readVertex((filaName), e -> {throw new RuntimeException("vertex file not found: shader/glsl/" + filaName);}),
                ShaderIO.readFragment((filaName), e -> {throw new RuntimeException("fragment file not found: shader/glsl/" + filaName);}));
    }

    /**
     * コンパイルをする必要はありませんが、属性の追加ができません。
     * @param filaName file name
     */
    public static GLSL readFile(File filaName) {
        GLSL re =  readFileCostumAttribute(filaName);
        re.compile();
        return re;
    }

    /**
     * 属性追加後に{@link GLSL#compile()}でコンパイルする必要があります。
     * @param filaName file name
     */
    public static GLSL readCostumAttribute(String filaName) {
        return new GLSL(
                ShaderIO.readVertex(filaName, e -> {throw new RuntimeException("vertex file not found: shader/glsl/" + filaName);}),
                ShaderIO.readFragment(filaName, e -> {throw new RuntimeException("fragment file not found: shader/glsl/" + filaName);}));
    }

    /**
     * コンパイルをする必要はありませんが、属性の追加ができません。
     * @param filaName file name
     */
    public static GLSL read(String filaName) {
        GLSL re =  readCostumAttribute(filaName);
        re.compile();
        return re;
    }

    public GLSL(String vSrc, String fSrc) {
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

        addAttribute(VERTEX ,3, GL_FLOAT, false, 0, AttribObjectArray.VERTICES);
        addAttribute(COLOR ,4, GL_FLOAT, false, 0, AttribObjectArray.COLORS);
        addAttribute(TEXTURE,4, GL_FLOAT, false, 0, AttribObjectArray.TEXTURES);

        for (int i = 0; i < attribs.size(); i++) {
            attribs.get(i).bindProgram(program, names.get(i));
        }

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            System.err.println("'glLinkProgram()' is failed.\n" +
                               glGetProgramInfoLog(program));
            System.exit(1);
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != GL_TRUE) {
            System.err.println("'glValidateProgram()' is failed.\n" +
                               glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public void addAttribute(int size, int type, boolean normalized, int stride, String name) {
        addAttribute(attribs.size(), size, type, normalized, stride, name);
    }

    private void addAttribute(int id, int size, int type, boolean normalized, int stride, String name) {
        attribs.add(id, new AttribObjectArray(attribs.size(), size, type, normalized, stride));
        names.add(id, name);
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
    public List<AttribObjectArray> getAttibs() {
        return attribs;
    }
}
