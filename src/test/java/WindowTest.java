import org.glcf2.FragUtil32;
import org.glcf2.Texture;
import org.glcf2.UniformNames;
import org.glcf2.component.Mouse;
import org.glcf2.component.MouseEvent;
import org.glcf2.component.glcomponent.GLComponentFactory;
import org.glcf2.component.glcomponent.GLEventID;
import org.glcf2.component.glcomponent.GLWindow;
import org.glcf2.programobject.IBO;
import org.glcf2.programobject.VBO;
import org.glcf2.io.ImageReader;
import org.glcf2.models.GLVBOModel;
import org.glcf2.shaders.GLSL;
import org.glcf2.vertex.ArrayModel;
import org.glcf2.vertex.ArrayModelFactory;
import org.linear.aid.LinearMath;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.*;

import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Math.*;
import static org.linear.main.matrix.MatrixUtil.transformation;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowTest {
    private static float size = 0.5f;
    private static Vector2d mpos = new Vector2d();
    private static int mfrag = 0;

    public static void main(String[] args) {
        GLWindow window = GLComponentFactory.createWindow(GLWindow.DrawingsType.LIST, new Vector2d(0.5, 0.5), "test 2", NULL, NULL, 0);
        window.init();

        Vector<?, ?>[][] verticies = donut(0.7f, 0.1f, 4096, 512);
//        Vector<?, ?>[][] verticies = cube(1);

        ArrayModel<Matrix4f, Vector4f> vs = ArrayModelFactory.createVertexModel((Vector4f[]) verticies[0]);
        var pos =       VBO.create(vs.getVerticies(), 3);
        var col =       VBO.create((Vector4f[])verticies[1], 4);
        var idx =       IBO.create((Vector3i[]) verticies[2], 3);

        var normals =   VBO.create((Vector4f[]) verticies[3], 3);

        GLSL mprg = GLSL.readFile(new File("test"));

        BufferedImage texImg = ImageReader.read(new File("その他/img_1.png"), e -> null);
        Texture texture = new Texture(texImg);

        GLVBOModel m = new GLVBOModel(pos, idx, mprg);
        m.setPrgogram(mprg);
        m.setVerties(pos);
        m.setIndicies(idx);
        m.setTexture(texture);
        m.setColor(col);
        m.addVBO(normals);

        GLVBOModel g = ground(-0.5f, 1f);
        g.setPrgogram(GLSL.read("glcf/component/glsl/button"));

        StringBuilder log = new StringBuilder();

        window.addDrawing(m);
//        window.addDrawing(g);

        window.setUniform((w, t, ml) -> {
            mprg.setUniform(UniformNames.TIME, (float) t);
            mprg.setUniformm(UniformNames.MAT4, new Matrix4f(Matrix4f.DIAGONAL)
                    .scale(size)
                    .translate((float) mpos.x * 2, (float) mpos.y * 2, 0)
                    .rotate(1f * t, new Vector4f(1, 1, 1, 0))
                    .toNewArray());
        });

        window.setFrameAction((l, t) -> {
            Mouse me = window.getEvent().getMouse();

            if (me.invoked()) {
                if (me.scrolled()) {
                    size -= (float) (pow(size * 0.7, 2) * me.getScrollY());
                }

                if (me.moved() && FragUtil32.fragIs(mfrag, MouseEvent.MOUSE_PRESS)) {
                    me.getEvent();
                    mpos.add(me.getDelta());
                    mfrag = FragUtil32.fragSetTrue(mfrag, MouseEvent.MOUSE_PRESS);
                }

                if (me.getEvent() == GLEventID.event.press()) mfrag = FragUtil32.fragSetTrue(mfrag, MouseEvent.MOUSE_PRESS);
                if (me.getEvent() == GLEventID.event.release()) mfrag = FragUtil32.fragSetFalse(mfrag, MouseEvent.MOUSE_PRESS);
            }
        });

        window.run();
        window.close();
    }

    public static Vector4f[] plygons(int...xyz) {
        Vector4f[] re = new Vector4f[xyz.length / 3];
        for (int i = 0; i < re.length; i++) {
            int g = i * 3;
            re[i] = new Vector4f(
                    xyz[g],
                    xyz[g + 1],
                    xyz[g + 2],
                    1
            );
        }

        return re;
    }

    public static Vector4d[] cube(final double d) {
        return new Vector4d[]{
                new Vector4d(-d, -d, -d, 1),
                new Vector4d(-d, -d, d, 1),
                new Vector4d(-d, d, d, 1),
                new Vector4d(-d, d, -d, 1),
                new Vector4d(-d, -d, -d, 1),
                new Vector4d(d, -d, -d, 1),
                new Vector4d(d, d, -d, 1),
                new Vector4d(-d, d, -d, 1),
                new Vector4d(d, d, -d, 1),
                new Vector4d(d, d, d, 1),
                new Vector4d(d, -d, d, 1),
                new Vector4d(d, -d, -d, 1),
                new Vector4d(d, d, -d, 1),
                new Vector4d(-d, d, -d, 1),
                new Vector4d(-d, -d, -d, 1),
                new Vector4d(-d, -d, d, 1),
                new Vector4d(d, -d, d, 1),
                new Vector4d(d, d, d, 1),
                new Vector4d(-d, d, d, 1)
        };
    }

    /**
     * @param r   ドーナツの半径
     * @param th  ドーナツの円柱の半径
     * @param div ドーナツの分割数
     * @param sph 円柱の分割数
     * @return pos, color, indices, nomal
     */
    public static Vector<? extends Number, ?>[][] donut(float r, float th, int div, int sph) {
        Vector4f[] dPols = new Vector4f[sph * (div + 1)];
        Vector4f[] color = new Vector4f[dPols.length];
        Vector3i[] idx = new Vector3i[dPols.length * 2];
        Vector4f[] normals = new Vector4f[idx.length];

        Matrix4f rMat = Matrix4f.DIAGONAL.clone();
        Matrix4f rdMat = Matrix4f.DIAGONAL.clone();
        Matrix4f tMat = Matrix4f.DIAGONAL.clone();
        final float sD = (float) (2 * PI / sph);
        final float dD = (float) (2 * PI / div);

        tMat.set(Matrix4f.DIAGONAL);
        tMat.translateX(r);
        for (int d = 0; d < div + 1; d++) {
            rMat.set(Matrix4f.DIAGONAL);
            rdMat.rotateZ(dD);
            for (int s = 0; s < sph; s++) {
                Vector4f sl = new Vector4f(th, 0, 0, 1);
                transformation(rMat.rotateY(sD), sl);
                transformation(tMat, sl);
                transformation(rdMat, sl);

                dPols[d * sph + s] = sl;
                color[d * sph + s] = new Vector4f((float) sin(toRadians((double) d / div * 360)), (float) cos(toRadians((double) d / div * 360)), 1, 1);
                idx[(d * sph + s) * 2] = new Vector3i(d * sph + s, d * sph + s + sph, d * sph + s + sph + 1);
                idx[(d * sph + s) * 2 + 1] = new Vector3i(d * sph + s + sph + 1, d * sph + s, d * sph + s + 1);
            }
        }

        for (int i = 0, len = idx.length; i < len; i++) {
            try {
                var tmp = LinearMath.normal3fPos(
                        dPols[idx[i].x],
                        dPols[idx[i].y],
                        dPols[idx[i].z]);
                normals[i] = new Vector4f(tmp.x, tmp.y, tmp.z, 1);
            } catch (Exception ignored) {
                normals[i] = new Vector4f();
            }
        }

        if (idx.length > sph) {
            for (int i = 0; i < sph * 2; i++) {
                idx[idx.length - sph * 2 + i] = idx[i];
            }
        }

        Vector4f tmp = new Vector4f(0, 0, 0, 1);

        return new Vector<?, ?>[][]{
                dPols,
                color,
                idx,
                normals
        };
    }

    public static Vector<? extends Number, ?>[][] cube(float size) {
        Vector4f[] pols = new Vector4f[8];
        Vector4f[] colors = new Vector4f[8];
        Vector3i[] idx = new Vector3i[12];
        Vector4f[] nls;

        pols[0] = new Vector4f(size, size, size, 1f);       // right top   back  0
        pols[1] = new Vector4f(-size, size, size, 1f);      // left  top   back  1
        pols[2] = new Vector4f(-size, size, -size, 1f);     // left  top   front 2
        pols[3] = new Vector4f(size, size, -size, 1f);      // right top   front 3
        pols[4] = new Vector4f(size, -size, size, 1f);      // right under back  4
        pols[5] = new Vector4f(-size, -size, size, 1f);     // left  under back  5
        pols[6] = new Vector4f(-size, -size, -size, 1f);    // left  under front 6
        pols[7] = new Vector4f(size, -size, -size, 1f);     // right under front 7

        colors[0] = new Vector4f(.5f,   1,      1,      1);
        colors[1] = new Vector4f(.5f,   1,      .5f,    1);
        colors[2] = new Vector4f(.5f,   .5f,    1,      1);
        colors[3] = new Vector4f(.5f,   .5f,    .5f,    1);
        colors[4] = new Vector4f(1,     .5f,    .5f,    1);
        colors[5] = new Vector4f(1,     .5f,    .5f,    1);
        colors[6] = new Vector4f(.2f,   1,      .5f,    1);
        colors[7] = new Vector4f(1,     .2f,    .5f,    1);

        idx[0] = new Vector3i(0, 1, 2); //top face
        idx[1] = new Vector3i(2, 3, 0);
        idx[2] = new Vector3i(4, 5, 6); //under
        idx[3] = new Vector3i(6, 7, 4);
        idx[4] = new Vector3i(0, 3, 7); //right
        idx[5] = new Vector3i(7, 4, 0);
        idx[6] = new Vector3i(1, 2, 6); //left
        idx[7] = new Vector3i(6, 5, 1);
        idx[8] = new Vector3i(2, 3, 7); //front
        idx[9] = new Vector3i(7, 6, 2);
        idx[10] = new Vector3i(0, 1, 5); //back
        idx[11] = new Vector3i(5, 4, 0);

        nls = normal(pols, idx);

        return new Vector<?, ?>[][]{
                pols,
                colors,
                idx,
                nls
        };
    }

    public static Vector4f[] normal(Vector4f[] vs, Vector3i[] idx) {
        Vector4f[] re = new Vector4f[idx.length];
        Vector4f a = new Vector4f(),
                b = new Vector4f(),
                c = new Vector4f();


        for (int i = 0, len = idx.length; i < len; i++) {
            a.set(vs[idx[i].x]);
            b.set(vs[idx[i].y]);
            c.set(vs[idx[i].z]);
            re[i] = LinearMath.normal3fPos(a, b, c);
            re[i].w = 0;
            re[i].nomalize();
            re[i].w = 1;
        }

        return re;
    }

    public static GLVBOModel ground(float h, float size) {
        var arrM = ArrayModelFactory.createVertexModel(new Vector4f[]{
                new Vector4f(-size, h, 0, 1),
                new Vector4f(size, h, 0, 1),
                new Vector4f(size + 0.1f, h, size * 10, 1),
                new Vector4f(-size + 0.1f, h, size * 10, 1),
        });

        var idx = IBO.create(new Vector3i[]{
                new Vector3i(0, 1, 2),
                new Vector3i(2, 3, 0)
        }, 3);

        GLVBOModel re = new GLVBOModel(VBO.create(arrM.getVerticies(), 3), idx, GLSL.read("glcf/component/glsl/button"));
        re.setColor(VBO.create(new Vector4f[]{
                new Vector4f(.5f, .5f, .5f, 1),
                new Vector4f(.5f, .5f, .5f, 1),
                new Vector4f(.5f, .5f, .5f, 1),
                new Vector4f(.5f, .5f, .5f, 1)
        }, 4));

        return re;
    }
}
