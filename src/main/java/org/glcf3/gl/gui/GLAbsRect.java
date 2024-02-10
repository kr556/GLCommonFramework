package org.glcf3.gl.gui;

import org.glcf3.*;
import org.glcf3.Window;
import org.glcf3.gl.Vertex;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.glcf3.FragUtil32.*;

import static org.glcf3.gl.gui.KeybordEventListener.*;
import static org.glcf3.gl.gui.MouseEventListener.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15.*;

public abstract class GLAbsRect extends Model implements GUI {
    private Window root;
    private Vector2d pos;
    private Vector2d size;
    private Shader shader;
    private List<GLAbsRect> guis = new ArrayList<>();
    private Texture texture;
    private Vector4f[] color;
    private MouseEventListener mEL;
    private KeybordEventListener kEL;
    private JoystickEventListener jEL;

    private Mouse mouse;
    private Keybord keybord;
    private Joystick joystick;

    protected transient int mouseFrag;
    protected transient HashMap<Integer, Integer> keyFrag = new HashMap<>();
    protected transient int joystickFrag;

    public GLAbsRect() {
        color = new Vector4f[] {
                new Vector4f(1),
                new Vector4f(1),
                new Vector4f(1),
                new Vector4f(1)
        };
        pos = new Vector2d();
        size = new Vector2d();
    }

    public GLAbsRect(double x, double y, double w, double h) {
        color = new Vector4f[] {
                new Vector4f(1),
                new Vector4f(1),
                new Vector4f(1),
                new Vector4f(1)
        };
        pos = new Vector2d(x, y);
        size = new Vector2d(w,h);
    }

    @Override
    public void drawing() {
        drawing(color);
    }

    protected void drawing(Vector4f...color) {
        if (shader != null) shader.bind();
        if (texture != null) texture.bind();

        glBegin(GL_QUADS);

        if (color != null && color.length >= 4) glColor4f(color[0].x, color[0].y, color[0].z, color[0].w);
        if (texture != null) glTexCoord2f(0, 0);
        glVertex2f((float) pos.x, (float) (pos.y + size.y));

        if (color != null && color.length >= 4) glColor4f(color[1].x, color[1].y, color[1].z, color[1].w);
        if (texture != null) glTexCoord2f(1, 0);
        glVertex2f((float) (pos.x + size.x), (float) (pos.y + size.y));

        if (color != null && color.length >= 4) glColor4f(color[2].x, color[2].y, color[2].z, color[2].w);
        if (texture != null) glTexCoord2f(1, 1);
        glVertex2f((float) (pos.x + size.x), (float) pos.y);

        if (color != null && color.length >= 4) glColor4f(color[3].x, color[3].y, color[3].z, color[3].w);
        if (texture != null) glTexCoord2f(0, 1);
        glVertex2f((float) pos.x, (float) pos.y);

        if (texture != null) texture.unbind();
    }

    @Override
    public void setRoot(Window root) {
        this.root = root;
        this.mouse = root.getEvent().getMouse();
        this.keybord = root.getEvent().getKeybord();
        this.joystick = root.getEvent().getJoystick();
    }

    @Override
    public void setPos(double x, double y) {
        pos.set(x, y);
    }

    @Override
    public void setPos(@Immutable Vector2d xy) {
        pos.set(xy);
    }

    @Override
    public void setSize(double w, double h) {
        size.set(w, h);
    }

    @Override
    public void setSize(@Immutable Vector2d wh) {
        size.set(wh);
    }

    public void setTexture(Texture tex) {
        texture = tex;
    }

    public void setColor(@Immutable Vector4f color) {
        setColor(color, color, color, color);
    }

    public void setColor(int hexColorRGBA) {
        float r = ((hexColorRGBA >> 24) & 0xff) / 256f;
        float g = ((hexColorRGBA >> 16) & 0xff) / 256f;
        float b = ((hexColorRGBA >> 8) & 0xff) / 256f;
        float a = (hexColorRGBA & 0xff) / 256f;

        setColor(new Vector4f(r, g, b, a));
    }

    public void setColor(Color color) {
        setColor((color.getRGB() << 8) | 0xff);
    }

    public void setColor(@Immutable  Vector4f ru,
                         @Immutable  Vector4f rd,
                         @Immutable  Vector4f lu,
                         @Immutable  Vector4f ld) {
        this.color[0].set(ru);
        this.color[1].set(rd);
        this.color[2].set(lu);
        this.color[3].set(ld);
    }

    public void setMouseEventListener(MouseEventListener listener) {
        this.mEL = listener;
    }

    public void setKeybordEventListener(KeybordEventListener listener) {
        this.kEL = listener;
    }

    public void setJoystickEventListener(JoystickEventListener listener) {
        this.jEL = listener;
    }

    @Override
    public Window getRoot() {
        return root;
    }

    @Override
    public Vector2d getPos() {
        return this.pos;
    }

    @Override
    public Vector2d getSize() {
        return this.size;
    }

    @Override
    public Model getModel() {
        return this;
    }

    @Override
    public boolean mouseHit() {
        Vector2d p = mouse.getPos();

        if (p.isNaN()) return false;

        return (p.x >= pos.x && p.x <= pos.x + size.x) &&
               (p.y >= pos.y && p.y <= pos.y + size.y);
    }

    public Vector4f[] getColors() {
        return color;
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    @Override
    public Vertex[] getVerticies() {
        return new Vertex[] {
                new Vertex((float) pos.x, (float) (pos.x + pos.y), 1, 1),
                new Vertex((float) (pos.x + size.x), (float) (pos.y + pos.y), 1, 1),
                new Vertex((float) (pos.x + size.x), (float) pos.y, 1, 1),
                new Vertex((float) pos.x, (float) pos.y,10, 1)
        };
    }

    @Override
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public void invokeMouse() {
        if (fragIs(mouseFrag, MOUSE_ENTER)) { // mouse inside action.
            mEL.mouseHitting(mouse);

            if (mouse.getEvent() == GLFW_PRESS) { // pressed action.
                mEL.mousePressed(mouse);
                mouseFrag = fragGetTrue(mouseFrag, MOUSE_PRESS);
            } else if (mouse.getEvent() == GLFW_RELEASE) { // released action.
                mEL.mouseReleased(mouse);

                if (fragIs(mouseFrag, MOUSE_PRESS))
                    mEL.mouseClicked(mouse); // clicked action.

                mouseFrag = fragGetFalse(mouseFrag, MOUSE_PRESS);
            }

            if (!mouseHit()) {
                mEL.mouseExited(mouse);
                mouseFrag = fragGetFalse(mouseFrag, MOUSE_ENTER);
            }
        } else { // outside action.
            if (mouseHit()) { // entered action.
                mEL.mouseEntered(mouse);
                mouseFrag = fragGetTrue(mouseFrag, MOUSE_ENTER);
            }
        }

        guis.forEach(ActionInvoker::invokeMouse);
    }

    @Override
    public void invokeKeybord() {
        int k;
        for (int i = 0, klen = keybord.getEventLenth(); i < klen; i++) {
            k = keybord.getKeys(i);

            if (fragIs(keybord.getKeyEvent(i).getEvent(), MOUSE_PRESS)) {
                kEL.keyPressed(keybord);

                keyFrag.put(keyFrag.get(k), fragGetTrue(keyFrag.get(k), KEYBORD_PRESS));
            } else {
                kEL.keyReleased(keybord);

                if (fragIs(keyFrag.get(k), KEYBORD_PRESS)) {
                    kEL.keyTyped(keybord);
                }

                keyFrag.put(keyFrag.get(k), fragGetFalse(keyFrag.get(k), KEYBORD_PRESS));
            }
        }
    }

    @Override
    public void invokeJoystick() {/**/}
}
