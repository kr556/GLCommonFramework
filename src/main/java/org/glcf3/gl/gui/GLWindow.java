package org.glcf3.gl.gui;

import org.glcf3.Drawing;
import org.glcf3.Immutable;
import org.glcf3.Texture;
import org.glcf3.Window;
import org.glcf3.gl.GLTexture;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector2i;
import org.linear.main.vector.Vector4i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocInt;

//TODO implements of methods.
public class GLWindow extends ArrayList<GUI> implements Window {
    private Vector2d screenSize;        // Pix
    private long window;
    private Vector4i bound;
    private String title;
    private Events events;

    private transient IntBuffer widthNow;
    private transient IntBuffer heightNow;
    private transient double viewSize;

    {
        bound = new Vector4i();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize = new Vector2d(d.width, d.height);
    }

    public GLWindow(Vector4i bound, String title) {
        this.bound.set(bound);
        this.title = title;

        init(bound, title);

        events = new EventsFrameFirst();
        events.init();
    }

    @Override
    public void init(Vector4i bound, String title) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(bound.z, bound.w, title, NULL, NULL);

        if (window == 0) throw new RuntimeException();

        try (MemoryStack stack = stackPush()) {
            this.widthNow = memAllocInt(1);
            this.heightNow = memAllocInt(1);

            glfwGetWindowSize(window, widthNow, heightNow);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window, bound.x, bound.y);
        }


        glfwSetErrorCallback((err, message) -> System.err.println("error[" + err + "]: " + message));

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
    }

    @Override
    public void run() {
        while (!shouldClose()) {
            forEach(GUI::invokeMouse);
            resizeViewPort();
            drawing();
        }

        close();
    }

    @Override
    public void drawing() {
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        forEach(Drawing::drawing);

        glEnd();
        glfwSwapBuffers(window);
    }

    @Override
    public void close() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    @Override
    public double getTime() {
        return glfwGetTime();
    }

    @Override
    public Vector4i getBound() {
        return bound.clone();
    }

    @Immutable
    @Override
    public Vector2i getSize() {
        return new Vector2i(bound.z, bound.w);
    }

    @Override
    public Vector2i getPos() {
        return new Vector2i(bound.x, bound.y);
    }

    @Override
    public Events getEvent() {
        return events;
    }

    @Override
    public boolean add(GUI gui) {
        gui.setRoot(this);
        return super.add(gui);
    }

    public void resizeViewPort() {
        glfwGetWindowSize(window, widthNow, heightNow);
        double wn = widthNow.get(0);
        double hn = heightNow.get(0);
        viewSize = Math.max(wn, hn);

        glViewport((int) ((wn - viewSize) / 2), (int) ((hn - viewSize) / 2), (int) viewSize, (int) viewSize);
    }

    private final class EventsFrameFirst implements Events {

        private MouseImplEFF mouse;
        private Keybord keybord;
        private Joystick joystick;

        private EventsFrameFirst() {
            mouse = new MouseImplEFF();
            keybord = new KeybordImplEFF_10();
            joystick = new JoystickImplEFF();
        }

        @Override
        public long getParent() {
            return window;
        }

        @Override
        public MouseImplEFF getMouse() {
            return mouse;
        }

        @Override
        public Keybord getKeybord() {
            return keybord;
        }

        @Override
        public Joystick getJoystick() {
            return joystick;
        }
        private class MouseImplEFF implements Mouse {
            private static Vector2d INV_Y = new Vector2d(1, -1);
//            private static Vector2d INV_X = new Vector2d(-1, 1);

            private int btn;
            private int ev;
            private int mod;
            private boolean invoked = false;
            private Vector2d pos;
            private Vector2d scroll;
            private transient boolean inside;
            private transient Vector2d oldPos = new Vector2d();
            private transient Vector2d oldScroll = new Vector2d();

            @Override
            public void init() {
                pos = Vector2d.NAN.clone();
                scroll = Vector2d.NAN.clone();

                glfwSetMouseButtonCallback(window, (w, b, e, m) -> {
                    this.btn = b;
                    this.ev = e;
                    this.mod = m;
                    invoked = true;
                });

                glfwSetScrollCallback(window, (w, sx, sy) -> {
                    scroll.set(sx, sy);
                    invoked = true;
                });

                glfwSetCursorPosCallback(window, (w, px, py) -> {
                    pos.set(px, py);
                    invoked = true;
                });
            }

            @Override
            public void reset() {
                oldPos.set(pos);
                oldScroll.set(scroll);

                scroll.set(0, 0);
                btn = -1;
                ev = -1;
                mod = -1;
                invoked = false;
            }

            @Override
            public void update() {/**/}

            @Override
            public int getButton() {
                return btn;
            }

            @Override
            public int getEvent() {
                return ev;
            }

            @Override
            public int getModifier() {
                return mod;
            }


            @Override
            public Vector2d getPos() {
                double sx = widthNow.get(0);
                double sy = heightNow.get(0);

                Vector2d re = pos.clone();
                re.x = (re.x - sx / 2) / viewSize * 2;
                re.y = ((sy - re.y) - sy / 2) / viewSize * 2;
                return re;
            }

            @Override
            public Vector2d getDeltaPix() {
                return pos.clone().sub(oldPos);
            }

            public Vector2d getDelta() {
                return getDeltaPix().mul(INV_Y).div(screenSize);
            }

            @Override
            public Vector2d getScroll() {
                return scroll.clone();
            }

            @Override
            public double getX() {
                return pos.x;
            }

            @Override
            public double getY() {
                return pos.y;
            }

            @Override
            public double getScrollX() {
                return scroll.x;
            }

            @Override
            public double getScrollY() {
                return scroll.y;
            }

            @Override
            public double getDeltaX() {
                return oldPos.x - pos.x;
            }

            @Override
            public double getDeltaY() {
                return oldPos.y - pos.y;
            }

            @Override
            public double sctollDeltaX() {
                return oldScroll.x - scroll.x;
            }

            @Override
            public double scrollDeltaY() {
                return oldScroll.y - scroll.y;
            }

            @Override
            public boolean invoked() {
                return invoked;
            }

            @Override
            public boolean moved() {
                return !pos.equals(oldPos);
            }

            @Override
            public boolean scrolled() {
                return !scroll.equals(oldScroll);
            }
        }

        private class KeybordImplEFF_10 implements Keybord {
            private final KeybordTemp[] keys = new KeybordTemp[10];

            private transient int evLen = 0;

            @Override
            public void init() {
                for (int i = 0; i < keys.length; i++) {
                    keys[i] = new KeybordTemp();
                }

                glfwSetKeyCallback(window, (w, k, sc, ac, m) -> {
                    if (evLen < keys.length) {
                        synchronized (keys) {
                            this.keys[evLen].key = k;
                            this.keys[evLen].scancode = sc;
                            this.keys[evLen].action = ac;
                            this.keys[evLen].mods = m;
                            evLen++;
                        }
                    }
                });
            }

            @Override
            public void update() {/**/}

            @Override
            public void reset() {
                for (int i = 0; i < 10; i++) {
                    keys[i].reset();
                }
                evLen = 0;
            }

            @Override
            public boolean invoked() {
                return keys[0].invoked();
            }

            @Override
            public int getEventLenth() {
                return evLen;
            }

            @Override
            public KeybordTemp getKeyEvent(int index) {
                return keys[index];
            }
        }

        class JoystickImplEFF implements Joystick {
            @Override
            public void init() {}

            @Override
            public void update() {}

            @Override
            public void reset() {}

            @Override
            public boolean invoked() {
                return false;
            }
        }
    }
}
