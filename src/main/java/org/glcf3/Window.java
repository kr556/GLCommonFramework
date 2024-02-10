package org.glcf3;

import org.glcf3.gl.gui.Events;
import org.linear.main.vector.Vector2f;
import org.linear.main.vector.Vector2i;
import org.linear.main.vector.Vector4f;
import org.linear.main.vector.Vector4i;

public interface Window {
    void init(Vector4i bound, String title);

    void run();

    void drawing();

    void close();

    boolean shouldClose();

    default void setPos(Vector2i pos) {
        setBound(getPos().x, getPos().y, pos.x, pos.y);
    }

    default void setSize(Vector2i size) {
        setBound(getPos().x, getPos().y, size.x, size.y);
    }

    default void setBound(int x, int y, int width, int height) {
        getBound().set(x, y, width, height);
    }

    default void setBound(Vector4i bound) {
        setBound(bound.x, bound.y, bound.z, bound.w);
    }

    default void setBound(Vector2i pos, Vector2i size) {
        setBound(pos.x, pos.y, size.x, size.y);
    }

    double getTime();

    Vector4i getBound();

    @Immutable
    Vector2i getSize();

    @Immutable
    Vector2i getPos();

    Events getEvent();
}
