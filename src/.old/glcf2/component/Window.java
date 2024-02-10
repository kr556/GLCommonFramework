package org.glcf2.component;

import org.glcf2.component.Events;
import org.glcf2.models.Model;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector2i;

public interface Window {
    /**
     * GLFWのパラメータを設定し、描画のための準備をします。
     */
    void init();

    /**
     * ループを開始します。
     */
    void run();

    /**
     * windowに描画します。
     */
    void drawing();

    /**
     * windowを閉じます。
     */
    void close();

    long getWindow();

    /**
     * Get size in pixel.
     * @return int vector
     */
    Vector2i getSizePix();

    /**
     * Get size in ratio. Screen size is 1.0
     * @return double vector
     */
    Vector2d getSize();

    Vector2i getPosPix();

    /**
     * max is 1.0
     * @return double vector
     */
    Vector2d getPos();

    /**
     * 現在のフレームでwindowを閉じる必要があるかどうか。
     * @return You must close window or not.
     */
    boolean shouldClose();

    Events getEvent();

    double getTime();

    void setSwapInterval(int interval);

    void setFrameAction(FrameAction frameAction);

    void setModel(Model model);

    Model getModel();

    @FunctionalInterface
    interface FrameAction {
        void invoke(long window, double time);
    }
}
