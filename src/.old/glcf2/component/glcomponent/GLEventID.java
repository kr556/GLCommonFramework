package org.glcf2.component.glcomponent;

import org.glcf2.component.Events;
import org.lwjgl.glfw.GLFW;

public final class GLEventID {
    public static final Events.EventID<Integer> event = new Events.EventID<>() {
        @Override
        public Integer press() {
            return GLFW.GLFW_PRESS;
        }

        @Override
        public Integer release() {
            return GLFW.GLFW_RELEASE;
        }
    };

    private GLEventID() {}
}
