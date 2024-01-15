package org.glcf2.component;

public interface MouseEvent {
    int MOUSE_IN = 1;
    int MOUSE_PRESS = 2;

    default void mousePressed(Mouse mouse) {}

    default void mouseReleased(Mouse mouse) {}

    default void mouseEntered(Mouse mouse) {}

    default void mouseExited(Mouse mouse) {}

    default void mouseClicked(Mouse mouse) {}

    default void mouseDragged(Mouse mouse) {}

    default void mouseMoved(Mouse mouse) {}

    default void mouseScrolled(Mouse mouse) {}

    default void mouseHitting(Mouse mosue) {}

    boolean mouseHit();
}
