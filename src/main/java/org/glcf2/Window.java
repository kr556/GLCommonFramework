package org.glcf2;

//import org.joml.Vector2L;

import org.liner.main.matrix.Matrix4f;
import org.liner.main.vector.Vector2d;
import org.liner.main.vector.Vector2i;
import org.liner.main.vector.Vector4f;
import org.liner.main.vector.Vector4i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long window;
    private Vector2i windowSize;

    private Mouse mouse;
    private Keybord keybord;

    private BooleanSupplier closeEvent;
    private FrameAction frameAction;
    private MouseAction mouseAction;
    private KeyAction keyAction;
    private Thread timer;

    /*================================<temp data>================================*/
    private transient boolean running;
    private transient double frameTime;
    private transient int millisMinFrameTime = 40;
    private transient IntBuffer windowWidthNow;
    private transient IntBuffer windowHeightNow;
    private transient Vector4i viewport = new Vector4i();

    public Window() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        Vector2d scDim = new Vector2d(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        double winMin = Math.min(scDim.x, scDim.y * 9 / 16d);
        window = glfwCreateWindow((int) (winMin * 16 / 9), (int) winMin, "cadea", NULL, NULL);
        windowSize = new Vector2i((int) (winMin * 16 / 9), (int) winMin);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        windowWidthNow = BufferUtils.createIntBuffer(1);
        windowHeightNow = BufferUtils.createIntBuffer(1);
        mouse = new Mouse();
        keybord = new Keybord();
        timer = new Thread(() -> {
        });
        closeEvent = () -> glfwWindowShouldClose(window);
        frameAction = (window) -> {
        };
        mouseAction = m -> {
        };
        keyAction = k -> {
        };

        glfwSetErrorCallback((err, message) -> System.err.println("error[" + err + "]: " + message));
    }

    public void close() {
        if (running) {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);

            glfwTerminate();
            glfwSetErrorCallback(null).free();
            running = false;
        }
    }

    public void start() {
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
    }

    private void render() {
    }

    public void update() {
        running = false;
        double stTime = glfwGetTime();

        startTimer();

        {
            glfwPollEvents();

            mouse.update();
            keybord.update();
            mouseAction.invoke(mouse);
            keyAction.invoke(keybord);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            resizeViewPort();

            /*イベント処理*/

            render();
            frameAction.invoke(window);

            glfwSwapBuffers(window); // swap the color buffers

            mouse.reset();
            keybord.reset();
        }

        endTimer();

        frameTime = glfwGetTime() - stTime;

        if (!closeEvent.getAsBoolean()) update();

        close();
    }

    public void resizeViewPort() {
        glfwGetWindowSize(window, windowWidthNow, windowHeightNow);

        int viewSideSize = Math.max(windowWidthNow.get(0), windowHeightNow.get(0));

        glViewport((windowWidthNow.get(0) - viewSideSize) / 2, (windowHeightNow.get(0) - viewSideSize) / 2,
                viewSideSize, viewSideSize);

        viewport = new Vector4i((windowWidthNow.get(0) - viewSideSize) / 2, (windowHeightNow.get(0) - viewSideSize) / 2,
                viewSideSize, viewSideSize);
    }

    public boolean closed() {
        return !glfwWindowShouldClose(window);
    }

    public int getWidth() {
        return this.windowSize.x;
    }

    public int getHeight() {
        return this.windowSize.y;
    }

    public double getFrameTime() {
        return frameTime;
    }

    public Mouse getMouse() {
        return this.mouse;
    }

    public void setFrameTime(double seconds) {
        this.millisMinFrameTime = (int) (seconds * 1000);
    }

    public void setCloseEvent(BooleanSupplier closeEvent) {
        this.closeEvent = closeEvent;
    }

    public void setFrameAction(FrameAction action) {
        this.frameAction = action;
    }

    public void setMouseAction(MouseAction mouseEvent) {
        this.mouseAction = mouseEvent;
    }

    public void setKeyAction(KeyAction keyAction) {
        this.keyAction = keyAction;
    }

    private void startTimer() {
        timer = new Thread(() -> {
            try {
                Thread.sleep(millisMinFrameTime);
            } catch (InterruptedException e) {
                System.exit(0x00000f00);
            }
        });
        timer.start();
    }

    private void endTimer() {
        try {
            timer.join();
        } catch (InterruptedException e) {
            System.exit(0x00000f01);
        }
    }

    public final class Mouse {
        private Vector2d scroll;
        private Vector2d speed;
        private boolean scrolled;

        /*================================<temp data>================================*/
        private transient boolean hitObj = false;
        private transient Vector2d pos = new Vector2d();
        private transient int eventLength = 0;
        private transient HashMap<Integer, Boolean> pressingList = new HashMap<>(); // <button, pressed>
        private transient List<MouseButton> buttonList = new ArrayList<>();

        public Mouse() {
            scroll = new Vector2d();
            speed = new Vector2d();
            scrolled = false;

            glfwSetCursorPosCallback(window, (window, x, y) -> {
                pos.x = ((x + getViewport().x) / getViewport().z) * 2 - 1;
                pos.y = ((windowHeightNow.get(0) - (y + getViewport().y)) / getViewport().w) * 2 - 1;
            });

            glfwSetScrollCallback(window, (window, sx, sy) -> {
                scroll.x = sx / viewport.z - 0.5;
                scroll.y = sy / viewport.w - 0.5;
                scrolled = true;
            });

            glfwSetMouseButtonCallback(window, (window, btn, ev, mod) -> {
                buttonList.add(new MouseButton(btn, ev, mod));
                if (ev == GLFW_PRESS) {
                    pressingList.put(btn, true);
                } else if (ev == GLFW_RELEASE) {
                    pressingList.remove(btn);
                }

                eventLength++;
            });
        }

        public void update() {
            double x0 = getX();
            double y0 = getY();

            speed.x = getX() - x0;
            speed.y = getY() - y0;
        }

        public void reset() {
            if (scrolled) {
                scrolled = false;
                scroll.x = 0;
                scroll.y = 0;
            }

            buttonList.clear();
            eventLength = 0;
        }

        public boolean pressing(int button) {
            Boolean re = pressingList.get(button);

            return re != null && re;
        }

        public int getButton(int eId) {
            return this.buttonList.get(eId).button;
        }

        public int getEvent(int eId) {
            return this.buttonList.get(eId).event;
        }

        public int getModifier(int eId) {
            return this.buttonList.get(eId).mod;
        }

        public int eventLength() {
            return eventLength;
        }

        public Vector2d getXY() {
            return new Vector2d(getX(), getY());
        }

        @Deprecated
        public Vector2d getSpeed() {
            return speed;
        }

        @Deprecated
        public Vector2d getScroll() {
            return scroll;
        }

        public double getX() {
            return pos.x;
        }

        public double getY() {
            return pos.y;
        }

        public double getScrolledX() {
            return scroll.x;
        }

        public double getScrolledY() {
            return scroll.y;
        }

        public double getSpeedX() {
            return speed.x;
        }

        public double getSpeedY() {
            return speed.y;
        }

        public boolean sctolledX() {
            return scroll.x != 0;
        }

        public boolean scrolledY() {
            return scroll.y != 0;
        }

        public boolean moved() {
            return speed.x != 0 && speed.y != 0;
        }

        public Vector4i getViewport() {
            return viewport;
        }

        private record MouseButton(int button, int event, int mod) {
        }
    }

    public final class Keybord {
        //================================<temp data>================================
        private transient int keyLength = 0;
        private transient HashMap<Integer, Boolean> pressingList = new HashMap<>();
        private transient List<Key> keyList = new ArrayList<>();

        public Keybord() {
            glfwSetKeyCallback(window, (window, key, scan, event, mod) -> {
                keyList.add(new Key(key, scan, event, mod));

                if (event == GLFW_PRESS) {
                    pressingList.put(key, true);
                } else if (event == GLFW_RELEASE) {
                    pressingList.remove(key);
                }

                keyLength++;
            });
        }

        public void update() {
        }

        public void reset() {
            keyList.clear();
            keyLength = 0;
        }

        public boolean pressing(int key) {
            Boolean re = pressingList.get(key);

            return re != null && re;
        }

        public boolean pressing(char key) {
            return pressing((int) key);
        }

        public int eventLength() {
            return keyLength;
        }

        public int getKey(int eId) {
            return this.keyList.get(eId).key;
        }

        public int getScan(int eId) {
            return this.keyList.get(eId).scan;
        }

        public int getEvent(int eId) {
            return this.keyList.get(eId).event;
        }

        public int getModifier(int eId) {
            return this.keyList.get(eId).mod;
        }

        public char getChar(int eId) {
            return (char) getKey(eId);
        }

        private record Key(int key, int scan, int event, int mod) {
        }
    }

    public interface FrameAction {
        void invoke(long window);
    }

    public interface MouseAction {
        void invoke(Mouse mouse);
    }

    public interface KeyAction {
        void invoke(Keybord keybord);
    }
}