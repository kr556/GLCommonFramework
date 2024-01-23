package org.glcf2.glcomponent;

import org.glcf2.component.GLCircleComponent;
import org.glcf2.component.GLRectangleComponent;
import org.glcf2.component.glcomponent.GLWindow;
import org.glcf2.component.glcomponent.GLComponentFactory;
import org.glcf2.component.Mouse;
import org.glcf2.shaders.GLSL;
import org.jetbrains.annotations.NotNull;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector4f;

import java.awt.*;

class WindowTest {
    private static GLWindow w;

    private static int mouse = 1;
    private static int key = 1;

    public static void main(String[] args) {
        w = GLComponentFactory.createWindow(GLWindow.DrawingsType.LIST, new Vector2d(0.5, 0.5), "test", 0, 0, 1);
        w.init();

        GLRectangleComponent glr = createRectangle(new Vector4f(-1, -1, 2, 2));
        GLRectangleComponent glb0 = createRectangle(new Vector4f(-0.5f, -0.5f, 1f, 1f));
        GLRectangleComponent glb1 = createRectangle(new Vector4f(-0.2f, -0.2f, 1f, 1f));

        glr.setBackground(Color.PINK);
        glr.setName("br-pink");

        glb0.setBackground(Color.CYAN);
        glb0.setName("b0-cyan");

        glb1.setBackground(Color.GREEN);
        glb1.setName("b1-gren");

        w.add(glb0);
        w.add(glb1);

        Matrix4f rot = Matrix4f.DIAGONAL.clone();
        glb0.transform((w, t) -> rot);
        glb1.transform((w, t) -> rot);

        w.run();
        w.close();
    }

    @NotNull
    private static GLCircleComponent createButton(Vector4f xywh) {
        GLCircleComponent glc = new GLCircleComponent(w);

        glc.setPos(new Vector2d(xywh.x, xywh.y));
        glc.setSize(new Vector2d(xywh.z, xywh.w));
        return glc;
    }

    @NotNull
    private static GLRectangleComponent createRectangle(Vector4f xywh) {
        GLRectangleComponent glc = new GLRectangleComponent(w) {
            @Override
            public void mouseEntered(Mouse mouse) {
                setBackground(getBackground4f().add(1f));
            }

            @Override
            public void mouseExited(Mouse mouse) {
                setBackground(getBackground4f().sub(1f));
            }
        };

        glc.setPos(new Vector2d(xywh.x, xywh.y));
        glc.setSize(new Vector2d(xywh.z, xywh.w));
        return glc;
    }
}