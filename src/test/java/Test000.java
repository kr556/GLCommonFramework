import org.glcf.main.VAO;
import org.glcf.main.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public class Test000 {
    public static void main(String[] args) {
        Window w = new Window();
        VFShader shader = shader();

        LogUtils.registeredLogCallback(System.out);
        LogUtils.registeredErrCallback(System.err);
        LogUtils.print(true);

        Attribute pos = new Attribute(0, 3, GL_FLOAT, 0, false, "pos", BindTypes.ATTRIB_LOCATION);
        Attribute color = new Attribute(1, 4, GL_FLOAT, 0, false, "color", BindTypes.FRAG_DATA_LOCATOIN);

        shader.compile(pos, color);
        shader.printLog();

        w.run();
    }

    public static VBO vbo() {
        VBO re = new VBO();
        re.bind(new float[]{
                1, 1, 1,
                1, 0, 1,
                0, 1, 1
        });
        return re;
    }

    public static VAO vao(Attribute attribute, VBO buffer) {
        VAO re = new VAO(attribute);
        re.bind(buffer);
        return re;
    }

    public static IBO ibo() {
        return new IBO(0, 1, 2);
    }

    public static VFShader shader() {
        StringBuilder v = new StringBuilder();
        StringBuilder f = new StringBuilder();
        try (BufferedReader vin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/a.vert")));
             BufferedReader fin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/a.frag")))) {
            vin.lines().forEach(l -> v.append("\n").append(l));
            fin.lines().forEach(l -> f.append("\n").append(l));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new VFShader(v.toString(), f.toString());
    }
}