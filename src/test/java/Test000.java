import org.glcf.main.VAO;
import org.glcf.main.*;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public class Test000 {
    public static void main(String[] args) throws Exception {
        Window w = new Window();
        VFShader shader = shader();
        Texture texture = new Texture(ImageIO.read(new File("C:/Users/nanac/画像/背景とか/GFamvg7b0AARtIv-c.jpg")));

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

        VAO vao = new VAO(2);
        vao.attach(0, pos, new Attribute(3, GL_FLOAT, 0, false, "pos"));
        vao.attach(1, color, new Attribute(4, GL_FLOAT, 0, false, "color"));
        vao.setFragdata(new Attribute(4, GL_FLOAT, 0, false, "glColor"));
        vao.attach(ibo);

        Attribute[] atts = vao.attributes();
        shader.compile(vao);

        Model m = new Model(vao);
        m.setDrawMode(GL_TRIANGLES);
        m.setShader(shader);
        m.setTexture(texture);

        w.add(m);
        w.run();
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