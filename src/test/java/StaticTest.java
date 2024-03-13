import org.glcf.main.*;
import org.glcf.main.gui.Window;
import org.linear.main.vector.Vector4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public final class StaticTest {
    static VAO vao;
    static VFShader shader;

    public static void main(String[] args) throws Exception {
        Window win = new Window();

        init();
        win.setBackground(new Vector4f(.5f, .2f, .5f, 1f));

//        win.add(() -> {
//            shader.use();
//
//            vao.draw(GL_TRIANGLES);
//        });

        win.run();
    }

    static void init() throws IOException {
        shader = getShader();

        VBO pos = new VBO();
        pos.attach(
                -.5f, .5f, 1,
                .5f, .5f, 1,
                .5f, -.5f, 1,
                -.5f, -.5f, 1
        );

        VBO color = new VBO();
        color.attach(
                1, 1, 1, 1,
                0, 0, 1, 1,
                0, 1, 0, 1,
                1, 0, 0, 1f
        );

        IBO ibo = new IBO(
                0, 1, 2,
                2, 3, 0
        );

        vao = new VAO(2);

        vao.attach(0, pos, new GLAttribute(3, GL_FLOAT, false, "pos"));
        vao.attach(1, color, new GLAttribute(4, GL_FLOAT, false, "color"));

        vao.setFragdata(new GLAttribute(4, GL_FLOAT, false, "glColor"));

        vao.attach(ibo);

        vao.bind();
        pos.bind();
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4, 0);

        pos.bind();
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 4, 12);

        shader.compile(vao);
    }


    public static VFShader getShader() {
        StringBuilder v = new StringBuilder();
        StringBuilder f = new StringBuilder();
        try (BufferedReader vin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/a.vs")));
             BufferedReader fin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/a.fs")))) {
            vin.lines().forEach(l -> v.append("\n").append(l));
            fin.lines().forEach(l -> f.append("\n").append(l));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new VFShader(v.toString(), f.toString());
    }
}
