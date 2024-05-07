import org.glcf.main.VAO;
import org.glcf.main.*;
import org.glcf.main.Window;
import org.glcf.util.ModelUtils;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Math.*;
import static org.glcf.main.VertexUtils.toArray;
import static org.linear.main.matrix.MatrixUtil.transformation;
import static org.lwjgl.opengl.GL20.*;

public class Test000 {
    public static void main(String[] args) throws Exception {
        Window w = new Window();
//        LWFShader shader = getLWFShader("d");
        VFShader shader = getVFShader("d");
//        Texture texture = new Texture(ImageIO.read(new File("C:/Users/nanac/画像/背景とか/GFamvg7b0AARtIv-c.jpg")));

        VAO vao = ModelUtils.donut(0.3f, 128, 512);
//        VAO vao1 = createVao.idonut(0.3f, 128, 512);
//        VAO vao = createVao.rectVAO(.5f, texture);

        shader.compile(vao);

        Model m = new Model(vao);
//        m.setDrawMode(GL_POINTS);
        m.setDrawMode(GL_TRIANGLES);
        m.setShader(shader);
//        m.setTexture(texture, "samp");
        m.setShaderUniform((s, t) -> {
            s.setUniformm("world", Matrix4f.DIAGONAL.clone()
                    .setRotateZ(PI / 5)
                    .rotateY(t)
            );
        });

//            Model m1 = new Model(vao);
//            m1.setDrawMode(GL_TRIANGLES);
//            m1.setShader(shader);
//    //        m1.setTexture(texture, "samp");
//            m1.setDrawSet((s, t) -> {
//                var world = Matrix4f.DIAGONAL.clone();
//                s.setUniformm("worlds", world
//    //                    .rotate(PI / 3, new Vector4f(1, 1, -1, 0))
//                        .setTranslateX(0.3f)
//                );
//            });

        w.add(m);
//        w.add(m1);

//        w.setBackground(new Vector4f(.5f, .2f, .5f, 1));
        w.set().background(new Vector4f(0));
        w.run();
    }

    public static VFShader getVFShader(String shaderName) {
        StringBuilder v = new StringBuilder();
        StringBuilder f = new StringBuilder();
        try (BufferedReader vin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/" + shaderName + ".vs")));
             BufferedReader fin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/" + shaderName +".fs")))) {
            vin.lines().forEach(l -> v.append("\n").append(l));
            fin.lines().forEach(l -> f.append("\n").append(l));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new VFShader(v.toString(), f.toString());
    }

    public static final class createVao {
        private createVao() {}


    }
}

