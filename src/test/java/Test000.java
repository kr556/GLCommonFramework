import org.glcf.main.VAO;
import org.glcf.main.*;
import org.glcf.main.gui.Window;
import org.jetbrains.annotations.NotNull;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector3i;
import org.linear.main.vector.Vector4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Math.*;
import static org.glcf.main.VertexUtils.toArray;
import static org.linear.main.matrix.MatrixUtil.transformation;
import static org.lwjgl.opengl.GL20.*;

public class Test000 {
    public static void main(String[] args) throws Exception {
        Window w = new Window();
        VFShader shader = getShader("d");
//        Texture texture = new Texture(ImageIO.read(new File("C:/Users/nanac/画像/背景とか/GFamvg7b0AARtIv-c.jpg")));

        VAO vao = donut(0.3f, 32, 512);
//        VAO vao = ring(.3f, 8);

        Attribute[] atts = vao.attributes();
        shader.compile(vao);

        Model m = new Model(vao);
//        m.setDrawMode(GL_POINTS);
        m.setDrawMode(GL_TRIANGLES);
        m.setShader(shader);

        m.setDrawSet((s, t) -> {
            s.setUniformm("local", new Matrix4f()
                    .setRotateY(t / 10));
            s.setUniformm("world", new Matrix4f()
                    .setRotateY(t / 10));
        });

        w.add(m);

        w.setBackground(new Vector4f(.5f, .2f, .5f, 1));
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

    public static VFShader getShader(String shaderName) {
        StringBuilder v = new StringBuilder();
        StringBuilder f = new StringBuilder();
        try (BufferedReader vin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/" + shaderName + ".vert")));
             BufferedReader fin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/gl/" + shaderName +".frag")))) {
            vin.lines().forEach(l -> v.append("\n").append(l));
            fin.lines().forEach(l -> f.append("\n").append(l));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new VFShader(v.toString(), f.toString());
    }

    public static VAO ring(float r, int p) {
        Vector4f[] all = new Vector4f[p];
        double t = PI / p * 2;
        Matrix4f tmp = Matrix4f.DIAGONAL.clone();
        for (int i = 0; i < p; i++) {
            Vector4f a = new Vector4f(1, 0, 0, 1);
            tmp.setRotateZ(t * i)
                    .transformation(a);
            all[i] = a.mul(r);
        }

        VBO pos = VBO.create(toArray(all));
        VBO color = VBO.create(new float[]{
                .2f, 0, 0, 1,
                .4f, 0, 0, 1,
                0, .2f, 0, 1,
                1, .4f, 0, 1,
                1, 0, .2f, 1,
                1, 0, .4f, 1,
                .2f, .2f, .2f, 1,
                .4f, .4f, .4f, 1,
        });

        VAO re = new VAO(2);
        re.attach(0, pos, new Attribute(4, GL_FLOAT, 0, false, "pos"));
        re.attach(1, color, new Attribute(4, GL_FLOAT , 0, false, "color"));

        re.attach(new IBO(0, 1, 2, 3, 4, 5, 6, 7, 0));

        return re;
    }

    /**
     * ドーナツ
     * @param s 大きさ
     * @param tc 円柱の一周の頂点数
     * @param rc 円周の頂点数
     * @return Vertex donut
     */
    public static VAO donut(float s, int tc, int rc) {
        Vector4f[] all = new Vector4f[tc * rc];
        double tht = PI / tc * 2;
        double thr = PI / rc * 2;
        Matrix4f tmp = Matrix4f.DIAGONAL.clone();
        for (int i = 0; i < tc; i++) {
            Vector4f a = new Vector4f(.5f, 0, 0, 1);
            tmp.setRotateZ(tht * i)
                    .transformation(a);
            for (int j = 0; j < rc; j++) {
                all[j * tc + i] = a.clone();
                tmp.setTranslateY(1);
                tmp.transformation(all[j * tc + i]);
            }
        }
        for (int i = 0; i < rc; i++) {
            tmp.setRotateX(i * thr);
            for (int j = 0; j < tc; j++)
                transformation(tmp, all[i * tc + j]);
        }
        for (Vector4f v : all)
            tmp.setScale(s).transformation(v);

        Vector4f[] colorV = new Vector4f[all.length];
        double d = 2 * PI / all.length;
        for (int i = 0; i < all.length; i++) {
            Vector4f c = new Vector4f((float) i / all.length);
            colorV[i] = c;
            c.w = 1;
        }

        Vector3i[] idx = new Vector3i[2 * tc * rc];
        for (int r = 0; r < rc; r++)
            for (int t = 0; t < tc; t++) {
                int i = r * tc + t;
                idx[i * 2] = new Vector3i(
                        i,
                        i + tc,
                        i + tc + 1
                );
                idx[i * 2 + 1] = new Vector3i(
                        i + 1,
                        i + tc + 1,
                        i
                );
            }

        int[] ix = toArray(idx, new int[idx.length * 3]);
        for (int i = (rc - 1) * tc * 6; i < ix.length; i++)
            if (ix[i] >= (all.length)) ix[i] -= all.length;

        IBO ibo = new IBO(ix);

        VBO pos = VBO.create(toArray(all));

        VBO color = VBO.create(toArray(colorV));

        VAO re = new VAO(2);
        re.attach(0, pos, new Attribute(4, GL_FLOAT, 0, false, "pos"));
        re.attach(1, color, new Attribute(4, GL_FLOAT, 0, false, "color"));
        re.attach(ibo);

        return re;
    }
}
