package org.glcf3.gl.gui;

public interface MouseEventListener {
    int MOUSE_ENTER = 1;
    int MOUSE_PRESS = 2;

    /**
     * Event when mouse pressed.
     */
    default void mousePressed(Mouse mouse) {}

    /**
     * Event when mouse released.
     */
    default void mouseReleased(Mouse mouse) {}

    /**
     * Event when mouse entered to this.
     */

    default void mouseEntered(Mouse mouse) {}

    /**
     * Event when mouse exited from this.
     */
    default void mouseExited(Mouse mouse) {}

    /**
     * Event when mosue clicked this.
     */
    default void mouseClicked(Mouse mouse) {}

    /**
     * Event when mouse draged.
     */
    default void mouseDragged(Mouse mouse) {}

    /**
     * Event when mouse moved.
     */
    default void mouseMoved(Mouse mouse) {}

    /**
     * Event when scrolled
     */
    default void mouseScrolled(Mouse mouse) {}

    /**
     * Event when mouse is indside.
     */
    default void mouseHitting(Mouse mouse) {}
}
