package org.glcf3.gl.gui;

public interface Events extends Event {
    long getParent();

    /**
     * ユーザーインターフェースとなる機器で何かしらのイベントが発生したかどうかを返します。
     * @return 発生したらtrue
     */
    @Override
    default boolean invoked() {
        return getMouse().invoked() ||
               getKeybord().invoked() ||
               getJoystick().invoked();
    }

    /**
     * マウスで発生したイベントを返します。
     * @return マウスのイベント
     */
    Mouse getMouse();

    /**
     * キーボードで発生したイベントを返します。
     * @return キーボードのイベント
     */

    Keybord getKeybord();

    /**
     * ジョイスティックで発生したイベントを返します。
     * @return ジョイスティックのイベント
     */
    Joystick getJoystick();

    /**
     * このインターフェースを使用するより前にかならず一度呼び出してください。
     */
    default void init() {
        getMouse().init();
        getKeybord().init();
        getJoystick().init();
    }

    /**
     * すべての機器のイベントの読み込みの状態をリセットします。
     */
    default void reset() {
        getMouse().reset();
        getKeybord().reset();
        getJoystick().reset();
    }

    /**
     * すべての機器のイベントの読み込みの状態を更新します。
     */
    default void update() {
        getMouse().update();
        getKeybord().update();
        getJoystick().update();
    }
}
