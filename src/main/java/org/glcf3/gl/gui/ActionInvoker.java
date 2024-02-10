package org.glcf3.gl.gui;

public interface ActionInvoker {
    void invokeMouse();

    void invokeKeybord();

    void invokeJoystick();

    /**
     * Collision detection
     */
    boolean mouseHit();
}
