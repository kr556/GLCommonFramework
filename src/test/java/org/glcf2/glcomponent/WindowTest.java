package org.glcf2.glcomponent;

import org.glcf2.component.Button;
import org.glcf2.component.Rectangle;
import org.glcf2.component.glcomponent.GLWindow;
import org.glcf2.component.glcomponent.GLComponentFactory;
import org.glcf2.component.Mouse;
import org.glcf2.shaders.GLSL;
import org.jetbrains.annotations.NotNull;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector4f;

import java.awt.*;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

class WindowTest {
    private static GLWindow w;

    private static int mouse = 1;
    private static int key = 1;

    public static void main(String[] args) {
        w = GLComponentFactory.createWindow(GLWindow.DrawingsType.LIST, new Vector2d(0.5, 0.5), "test", 0, 0, 1);
        w.init();

        Rectangle glr = createRectangle(new Vector4f(-1, -1, 2, 2));
        Rectangle glb0 = createRectangle(new Vector4f(-0.5f, -0.5f, 1f, 1f));
        Rectangle glb1 = createRectangle(new Vector4f(-0.2f, -0.2f, 1f, 1f));

        glr.setBackground(Color.PINK);
        glr.setName("br-pink");

        glb0.setBackground(Color.CYAN);
        glb0.setName("b0-cyan");

        glb1.setBackground(Color.GREEN);
        glb1.setName("b1-gren");

        w.add(glb0);
        glb0.add(glb1);

        w.setFrameAction((wl, t) -> {
        });

        w.run();
        w.close();
    }

    @NotNull
    private static Button createButton(Vector4f xywh) {
        Button glc = new Button(w);

        glc.setPos(new Vector2d(xywh.x, xywh.y));
        glc.setSize(new Vector2d(xywh.z, xywh.w));
        glc.setShader(GLSL.read("glcf/component/glsl/button"));
        return glc;
    }

    @NotNull
    private static Rectangle createRectangle(Vector4f xywh) {
        Rectangle glc = new Rectangle(w);

        glc.setPos(new Vector2d(xywh.x, xywh.y));
        glc.setSize(new Vector2d(xywh.z, xywh.w));
        glc.setShader(GLSL.read("glcf/component/glsl/button"));
        return glc;
    }
}