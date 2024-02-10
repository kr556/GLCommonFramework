/*import org.glcf2.FragUtil32;
import org.glcf2.GLMatrixUtil;
import org.glcf2.Texture;
import org.glcf2.UniformNames;
import org.glcf2.component.Mouse;
import org.glcf2.component.MouseEvent;
import org.glcf2.component.glcomponent.GLComponentFactory;
import org.glcf2.component.glcomponent.GLEventID;
import org.glcf2.component.glcomponent.GLWindow;
import org.glcf2.models.Visible;
import org.glcf2.programobject.IBO;
import org.glcf2.programobject.VBO;
import org.glcf2.io.ImageReader;
import org.glcf2.models.GLVBOModel;
import org.glcf2.shaders.GLSL;
import org.glcf2.texture.GLTexture;
import org.glcf2.vertex.ArrayModel;
import org.glcf2.vertex.ArrayModelFactory;*/
import org.glcf3.Texture;
import org.glcf3.gl.GLTexture;
import org.glcf3.gl.gui.GLAbsRect;
import org.glcf3.gl.gui.GLButton;
import org.glcf3.gl.gui.GLWindow;

import org.linear.main.LinearMath;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.*;

import java.io.File;
import java.io.IOException;

import static java.lang.Math.*;
import static org.linear.main.matrix.MatrixUtil.transformation;

public class WindowTest {
    private static float size = 0.5f;
    private static float rot = 0;
    private static Vector2d mpos = new Vector2d();
    private static int mfrag = 0;

    public static void main(String[] args) throws IOException {
        GLWindow win = new GLWindow(new Vector4i(100, 100, 1000, 1000), "title");

        GLButton gui = new GLButton(-0.5, -0.5, 1, 1) {};
        Texture tex = GLTexture.read(new File("その他/img_1.png"));

        gui.setColor(   new Vector4f(.2f, .1f, .5f, 1),
                new Vector4f(1, .5f, .5f, 1),
                new Vector4f(.5f, .5f, 1, 1),
                new Vector4f(.5f, 1, .5f, 1));

        gui.setTexture(tex);

        win.add(gui);

        win.run();
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

        colors[0] = new Vector4f(.5f,   1,      1,      0.5f);
        colors[1] = new Vector4f(.5f,   1,      .5f,    0.5f);
        colors[2] = new Vector4f(.5f,   .5f,    1,      0.5f);
        colors[3] = new Vector4f(.5f,   .5f,    .5f,    0.5f);
        colors[4] = new Vector4f(1,     .5f,    .5f,    0.5f);
        colors[5] = new Vector4f(1,     .5f,    .5f,    0.5f);
        colors[6] = new Vector4f(.2f,   1,      .5f,    0.5f);
        colors[7] = new Vector4f(1,     .2f,    .5f,    0.5f);

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
}
