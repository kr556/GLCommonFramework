package org.glcf2.component;

import org.glcf2.component.glcomponent.GLAbsComponent;
import org.glcf2.shaders.GLSL;
import org.linear.main.vector.Vector2d;

public class GLRectangleComponent extends GLAbsComponent {
    public GLRectangleComponent(Component parent) {
        super(parent);

        setShader(GLSL.read("glcf/component/glsl/rec"));
    }

    @Override
    public boolean mouseHit() {
        Vector2d p = mouse.getPos();

        if (p.isNaN()) return false;

        return (p.x >= pos.x && p.x <= pos.x + size.x) &&
               (p.y >= pos.y && p.y <= pos.y + size.y);
    }
}
