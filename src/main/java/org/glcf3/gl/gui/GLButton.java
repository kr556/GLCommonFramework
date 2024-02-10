package org.glcf3.gl.gui;

import org.glcf3.FragUtil32;
import org.glcf3.Immutable;
import org.linear.main.vector.Vector4f;

import static org.glcf3.FragUtil32.*;

public class GLButton extends GLAbsRect implements MouseEventListener {
    @Immutable public static Vector4f PRESS_COLOR_DIFFERENCE = new Vector4f(.2f, .2f, .2f, 0);
    @Immutable public static Vector4f ENTER_COLOR_DIFFERENCE = new Vector4f(.1f, .1f, .1f, 0);

    private MouseAction action = m -> {};
    private transient Vector4f tmpv4 = new Vector4f();

    public GLButton(double x, double y, int w, int h) {
        super(x, y, w, h);

        setMouseEventListener(this);
    }

    @Override
    public void drawing() {
        if (mouseFrag != 0) {
            Vector4f[] c = getColors();

            if (fragIs(mouseFrag, MOUSE_PRESS)) {
                drawing(
                        c[0].add(PRESS_COLOR_DIFFERENCE, tmpv4),
                        c[1].add(PRESS_COLOR_DIFFERENCE, tmpv4),
                        c[2].add(PRESS_COLOR_DIFFERENCE, tmpv4),
                        c[3].add(PRESS_COLOR_DIFFERENCE, tmpv4)
                );
            } else if (fragIs(mouseFrag, MOUSE_ENTER)) {
                drawing(
                        c[0].add(ENTER_COLOR_DIFFERENCE, tmpv4),
                        c[1].add(ENTER_COLOR_DIFFERENCE, tmpv4),
                        c[2].add(ENTER_COLOR_DIFFERENCE, tmpv4),
                        c[3].add(ENTER_COLOR_DIFFERENCE, tmpv4)
                );
            }
        } else
            drawing(getColors());
    }

    public void setButtonAction(MouseAction actiion) {
        if (actiion != null) this.action = actiion;
    }

    @Override
    public void mouseClicked(Mouse mouse) {
        if (mouse != null) action.invoke(mouse);
    }
}
