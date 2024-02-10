package org.glcf3.gl.gui;

import org.glcf3.Drawing;
import org.glcf3.Immutable;
import org.glcf3.Model;
import org.glcf3.Window;
import org.linear.main.vector.Vector2d;

public interface GUI extends
        Drawing, ActionInvoker {
    void setRoot(Window root);

    void setPos(double x, double y);

    void setPos(@Immutable Vector2d xy);

    void setSize(double w, double h);

    void setSize(@Immutable Vector2d wh);

    Window getRoot();

    Vector2d getPos();

    Vector2d getSize();

    Model getModel();
}
