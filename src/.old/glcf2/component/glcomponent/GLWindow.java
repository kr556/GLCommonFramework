package org.glcf2.component.glcomponent;

import org.glcf2.Drawing;
import org.glcf2.GLMatrixUtil;
import org.glcf2.Shader;
import org.glcf2.component.*;
import org.glcf2.models.Model;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.util.*;
import java.util.function.BooleanSupplier;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class GLWindow implements Window, Component {
    public enum DrawingsType {
        LIST,
        ARRAY;
    }

    protected DrawingsType drawingType_;

    private final long window;
    private final long monitor;
    private final long share;
    private final Vector2iPointer pos;
    private final Vector2iPointer size;
    private int swapInterval;
    private double fpsSet;
    private String title;
    private BooleanSupplier shouldClose;
    private Events event;
    private FrameAction frameAction;
    private Model windowModel;
    private List<Drawing> drawings = new ArrayList<>();
    private UniformSetter uniSet;
    int maxLayer;

    private transient int viewSize;
    private transient double fpsGet;
    private transient double runTime;
    private transient Vector2iPointer screenSize;
    private transient Vector2iPointer sizeNow = new Vector2iPointer(0, 0);

    {
        java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        screenSize = new Vector2iPointer(d.width, d.height);
        event = new EventsFrameFirst();
        frameAction = (w, t) -> {};
        swapInterval = 1;
    }

    protected GLWindow(Vector2d windowSize, String title, long monitor, long share) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow((int) (screenSize.x() * windowSize.x), (int) (screenSize.y() * windowSize.y), title, monitor, share);

        if (window == NULL) throw new RuntimeException();

        try (MemoryStack stack = stackPush()) {
            this.size = new Vector2iPointer(stack.mallocInt(1), stack.mallocInt(1));

            glfwGetWindowSize(window, size.x, size.y);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window, (vidmode.width() - size.x()) / 2, (vidmode.height() - size.y()) / 2);
        }

        this.pos = new Vector2iPointer();
        this.shouldClose = () -> glfwWindowShouldClose(window);
        this.monitor = monitor;
        this.share = share;
        this.title = title;

        event.init();

        glfwSetErrorCallback((err, message) -> System.err.println("error[" + err + "]: " + message));
    }

    @Override
    public void init() {
        glfwMakeContextCurrent(window);
        glfwSwapInterval(swapInterval);
        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
    }

    @Override
    public void run() {
        while (!shouldClose()) {
            final double startTime = runTime;

            event.reset();
            event.update();

            drawing();
            invokeMouseAction();

            runTime = glfwGetTime();

            fpsGet = 1 / (runTime - startTime);
        }
    }

    private void updateParameter() {
        glfwGetWindowSize(window, sizeNow.x, sizeNow.y);
    }

    @Override
    public void drawing() {
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);

        frameAction.invoke(window, runTime);
        if (uniSet != null) uniSet.set(window, runTime, null);

        resizeViewPort();

        Arrays.stream(getAll()).forEach(Drawing::drawing);

        for (Drawing drawing : drawings) {
            if (Objects.nonNull(drawing)) drawing.drawing();
        }

        glEnd();
        glfwSwapBuffers(window);
    }

    @Override
    public void close() {
        if (shouldClose()) {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);

            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    public void resizeViewPort() {
        glfwGetWindowSize(window, sizeNow.x, sizeNow.y);

        viewSize = Math.max(sizeNow.x(), sizeNow.y());

        glViewport((sizeNow.x() - viewSize) / 2, (sizeNow.y() - viewSize) / 2,
                viewSize, viewSize);
    }

    @Override
    public void invokeMouseAction() {
        for (Component c : getAll()) {
            c.invokeMouseAction();
        }
    }

    @Override
    public long getWindow() {
        return window;
    }

    @Override
    public Vector2i getSizePix() {
        return pos.toVector2i();
    }

    @Override
    public Vector2d getSize() {
        return new Vector2d((double) size.x() / screenSize.x(), (double) size.y() / screenSize.y());
    }

    @Override
    public Vector2i getPosPix() {
        return size.toVector2i();
    }

    @Override
    public void setSize(Vector2i size) {
        final int x = size.x;
        final int y = size.y;

        synchronized (this.size) {
            this.size.set(x, y);
        }

        glfwSetWindowSize(window, x, y);
        resizeViewPort();
    }

    @Override
    public void setSize(Vector2d size) {
        final int x = (int) (screenSize.x() * size.x);
        final int y = (int) (screenSize.y() * size.y);

        synchronized (this.size) {
            this.size.set(x, y);
        }

        glfwSetWindowSize(window, x, y);
        resizeViewPort();
    }

    @Override
    public synchronized void setPos(Vector2i pos) {
        final int x = pos.x;
        final int y = pos.y;
        this.pos.set(x, y);
        glfwSetWindowPos(window, x, y);
    }

    @Override
    public synchronized void setPos(Vector2d pos) {
        glfwSetWindowPos(window, (int) (pos.x * screenSize.x()), (int) (pos.y * screenSize.y()));
    }

    @Override
    public Vector2d getPos() {
        return new Vector2d((double) pos.x() / screenSize.x(), (double) pos.y() / screenSize.y());
    }

    @Override
    public boolean shouldClose() {
        return shouldClose.getAsBoolean();
    }

    @Override
    public Events getEvent() {
        return event;
    }

    @Override
    public double getTime() {
        return glfwGetTime();
    }

    @Override
    public Vector2i getAbsPos() {
        return pos.toVector2i();
    }

    @Override
    public Vector2i getAbsSize() {
        return size.toVector2i();
    }

    @Override
    public GLWindow getRoot() {
        return this;
    }

    @Override
    public Shader getShader() {
        return getModel().getShader();
    }

    @Override
    public void setShader(Shader shader) {
        getModel().setShader(shader);
    }

    @Override
    public synchronized void setModel(Model model) {
        windowModel = model;
    }

    @Override
    public Model getModel() {
        return windowModel;
    }

    public void addDrawing(Drawing m) {
        drawings.add(m);
    }

    @Override
    public void setSwapInterval(int interval) {
        swapInterval = interval;
    }

    public void setFrameAction(FrameAction frameAction) {
        this.frameAction = frameAction;
    }

    @Override
    public void setUniform(UniformSetter set) {
        this.uniSet = set;
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
                double sx = sizeNow.x();
                double sy = sizeNow.y();

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
                return getDeltaPix().mul(INV_Y).div(screenSize.toVector2d());
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
            public void init() {

            }

            @Override
            public void update() {

            }

            @Override
            public void reset() {

            }

            @Override
            public boolean invoked() {
                return false;
            }
        }
    }
}
