import org.glcf.main.VAO;
import org.glcf.main.*;
import org.glcf.main.gui.Window;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector3f;
import org.linear.main.vector.Vector3i;
import org.linear.main.vector.Vector4f;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
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
        Texture texture = new Texture(ImageIO.read(new File("C:/Users/nanac/画像/背景とか/GFamvg7b0AARtIv-c.jpg")));

//        VAO vao = createVao.idonut(0.3f, 256, 1024);
        VAO vao = createVao.rectVAO(.5f, texture);

        GLAttribute[] atts = vao.attributes();
        shader.compile(vao);

        Model m = new Model(vao);
//        m.setDrawMode(GL_POINTS);
        m.setDrawMode(GL_TRIANGLES);
        m.setShader(shader);
        m.setTexture(texture, "samp");

        w.add(m);

//        w.setBackground(new Vector4f(.5f, .2f, .5f, 1));
        w.setBackground(new Vector4f(0));
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

    public static LWFShader getLWFShader(String shaderName) {
        StringBuilder l = new StringBuilder();
        StringBuilder w = new StringBuilder();
        StringBuilder f = new StringBuilder();
        try (BufferedReader lin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/lwf/" + shaderName + ".ls")));
             BufferedReader win = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/lwf/" + shaderName + ".ws")));
             BufferedReader fin = new BufferedReader(new InputStreamReader(Test000.class.getResourceAsStream("glcf/lwf/" + shaderName +".fs")))) {
            lin.lines().forEach(ln -> l.append("\n").append(ln));
            win.lines().forEach(ln -> w.append("\n").append(ln));
            fin.lines().forEach(ln -> f.append("\n").append(ln));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new LWFShader(l.toString(), w.toString(), f.toString());
    }

    public static final class createVao {
        private createVao() {}

        public static VAO rectVAO(float size, Texture texture) {
            IBO ibo = new IBO(
                    0, 1, 2,
                    2, 3, 0
            );

            VBO pos = VBO.create(new float[]{
                    -size, size, 0,
                    size, size, 0,
                    size, -size, 0,
                    -size, -size, 0
            });

            VBO color = VBO.create(new float[]{
                    1, 1, 1, 1,
                    0, 0, 1, 1,
                    0, 1, 0, 1,
                    1, 0, 0, 1f
            });

            VBO tex = VBO.create(new float[]{
                    0, 0,
                    1, 0,
                    1, 1,
                    0, 1f
            });

            VAO vao = new VAO(2);
            vao.attach(0, pos, new GLAttribute(3, GL_FLOAT, false, "pos"));
            vao.attach(1, color, new GLAttribute(4, GL_FLOAT, false, "color"));
//            if (texture != null) vao.attach(2, tex, new GLAttribute(2, GL_FLOAT, false, "tex"));
            vao.setFragdata(new GLAttribute(4, GL_FLOAT, false, "glColor"));
            vao.attach(ibo);
            vao.push();

            return vao;
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
            re.attach(0, pos, new GLAttribute(4, GL_FLOAT, false, "pos"));
            re.attach(1, color, new GLAttribute(4, GL_FLOAT , false, "color"));

            re.attach(new IBO(0, 1, 2, 3, 4, 5, 6, 7, 0));

            return re;
        }

        public static IVAO idonut(float s, int tc, int rc) {
            Object[] d = donutData(s, tc, rc);

            IBO ibo = new IBO((int[]) d[2]);

            VBO pos = VBO.create((float[]) d[0]);

            VBO color = VBO.create((float[]) d[1]);

            IVAO re = new IVAO(2);
            re.attach(0, pos, new GLAttribute(3, GL_FLOAT, false, "pos"));
            re.attach(1, color, new GLAttribute(4, GL_FLOAT, false, "color"));
            re.attach(ibo);
            re.push();

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
            Object[] d = donutData(s, tc, rc);

            IBO ibo = new IBO((int[]) d[2]);

            VBO pos = VBO.create((float[]) d[0]);

            VBO color = VBO.create((float[]) d[1]);

            VAO re = new VAO(2);
            re.attach(0, pos, new GLAttribute(3, GL_FLOAT, false, "pos"));
            re.attach(1, color, new GLAttribute(4, GL_FLOAT, false, "color"));
            re.attach(ibo);
            re.push();

            return re;
        }

        private static Object[] donutData(float s, int tc, int rc) {
            var all = new Vector3f[tc * rc];
            var normal = new Vector3f[all.length];
            double tht = PI / tc * 2;
            double thr = PI / rc * 2;
            Matrix4f tmp = Matrix4f.DIAGONAL.clone();
            for (int i = 0; i < tc; i++) {
                var a = new Vector3f(.5f, 0, 0);
                tmp.setRotateZ(tht * i)
                        .transformation(a);
                for (int j = 0; j < rc; j++) {
                    all[j * tc + i] = a.clone();
                    tmp.setTranslateY(1)
                            .transformation(all[j * tc + i]);
                    tmp.setScale(s).rotateX(j * thr)
                            .transformation(all[j * tc + i]);
                }
            }

            Vector4f[] colorV = new Vector4f[all.length];
            for (int i = 0; i < all.length; i++) {
                colorV[i] = new Vector4f();
                colorV[i].x = (float) sin(i * PI / all.length);
                colorV[i].y = (float) sin(0.7 + i * PI * 2 / all.length);
                colorV[i].z = 1;
                colorV[i].w = 1;
            }

            Vector3i[] idx = new Vector3i[2 * tc * rc];
            for (int r = 0; r < rc; r++)
                for (int t = 0; t < tc; t++) {
                    int i = r * tc + t;
                    idx[i * 2] = new Vector3i(
                            i,
                            i + tc + 1,
                            i + 1
                    );
                    idx[i * 2 + 1] = new Vector3i(
                            i + tc + 1,
                            i + tc + 2,
                            i + 1
                    );
                }

            int[] ix = toArray(idx, new int[idx.length * 3]);
            for (int i = (rc - 1) * tc * 6; i < ix.length; i++)
                if (ix[i] >= (all.length)) ix[i] -= all.length;
            return new Object[]{
                    toArray(all),
                    toArray(colorV),
                    ix
            };
        }
    }
}

