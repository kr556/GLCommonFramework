package org.glcf2.component;


import org.glcf2.component.glcomponent.GLAbsComponent;
import org.linear.main.vector.Vector2d;

public class GLCircleComponent extends GLAbsComponent {
    public GLCircleComponent(Component parent) {
        super(parent);
    }

    @Override
    public boolean mouseHit() {
        Vector2d mp = mouse.getPos().clone();

        if (mp.isNaN()) return false;

        //TODO 楕円対応
        return mp.distance(getPos()) < getSize().x;
    }
}
