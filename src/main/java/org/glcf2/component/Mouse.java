package org.glcf2.component;

import org.linear.main.vector.Vector2d;

public interface Mouse extends Event {
    int getButton();

    int getEvent();

    int getModifier();

    Vector2d getPos();

    Vector2d getDeltaPix();

    Vector2d getDelta();

    Vector2d getScroll();

    double getX();

    double getY();

    double getScrollX();

    double getScrollY();

    double getDeltaX();

    double getDeltaY();

    double sctollDeltaX();

    double scrollDeltaY();

    boolean moved();

    boolean scrolled();
}
