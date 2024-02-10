package org.glcf2.component.glcomponent;


import org.glcf2.component.Component;
import org.glcf2.component.glcomponent.GLAbsComponent;
import org.glcf2.shaders.GLSL;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class GLCircleComponent extends GLAbsComponent {
    public GLCircleComponent(Component parent) {
        super(parent);

        setShader(GLSL.read("glcf/component/glsl/cir"));
    }

    @Override
    public boolean mouseHit() {
        Vector2d p = mouse.getPos();

        if (p.isNaN()) return false;

        return (p.x >= pos.x && p.x <= pos.x + size.x) &&
               (p.y >= pos.y && p.y <= pos.y + size.y);
    }
}
