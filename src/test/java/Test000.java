import org.glcf.main.VAO;
import org.glcf.main.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public class Test000 {
    public static void main(String[] args) throws Exception {
        Window w = new Window();
        VFShader shader = getShader();
        Texture texture = new Texture(ImageIO.read(new File("C:/Users/nanac/画像/背景とか/GFamvg7b0AARtIv-c.jpg")));

        VAO vao = getVao();

        Attribute[] atts = vao.attributes();
        shader.compile(vao);

        Model m = new Model(vao) {
            @Override
            public void drawing() {
                shader.use();

                vao.draw(GL_TRIANGLES);

                shader.disuse();
            }
        };
        m.setDrawMode(GL_TRIANGLES);
        m.setShader(shader);
        m.setTexture(texture);

        w.add(m);
        w.run();
    }

    @NotNull
    private static VAO getVao() {
        IBO ibo = new IBO(
                0, 1, 2,
                2, 3, 0
        );

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

        VBO tex = new VBO();
        tex.attach(
                0, 0,
                1, 0,
                1, 1,
                0, 1f
        );

        VAO vao = new VAO(3);
        vao.attach(0, pos, new Attribute(3, GL_FLOAT, 0, false, "pos"));
        vao.attach(1, color, new Attribute(4, GL_FLOAT, 0, false, "color"));
        vao.attach(2, tex, new Attribute(2, GL_FLOAT, 0, false, "tex"));
        vao.setFragdata(new Attribute(4, GL_FLOAT, 0, false, "glColor"));
        vao.attach(ibo);
        return vao;
    }

    public static VFShader getShader() {
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