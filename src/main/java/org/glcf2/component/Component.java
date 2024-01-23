package org.glcf2.component;

import org.glcf2.Drawing;
import org.glcf2.component.glcomponent.GLWindow;
import org.glcf2.component.glcomponent.UniformSetter;
import org.glcf2.models.Model;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector2i;

import java.util.function.Predicate;

public non-sealed interface Component extends Drawing, MouseActionInvoker {
    void setSize(Vector2i size);

    void setSize(Vector2d size);

    void setPos(Vector2i pos);

    void setPos(Vector2d pos);

    void add(Component[] parts);

    void add(Component part);

    Component get(int index);

    Component[] getAll();

    Vector2d getPos();

    Vector2d getSize();

    /**
     * filterがtrueとなる要素を削除します。
     */
    void remove(Predicate<Component> filter);

    void removeAll();

    /**
     * 画面上での座標をピクセル単位で返します。
     */
    Vector2i getAbsPos();

    /**
     * 画面上での大きさをピクセル単位で返します。
     */
    Vector2i getAbsSize();

    void setModel(Model model);

    Model getModel();

    Window getRoot();

    void setUniform(UniformSetter set);
}
