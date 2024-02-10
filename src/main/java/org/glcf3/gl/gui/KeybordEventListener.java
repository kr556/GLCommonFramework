package org.glcf3.gl.gui;

public interface KeybordEventListener {
    int KEYBORD_PRESS = 2;

    default void keyPressed(Keybord keybord) {}

    default void keyReleased(Keybord keybord) {}

    default void keyTyped(Keybord keybord) {}
}
