package org.glcf2.component;

/**
 * マウスイベントが発生した際のアクション。関数型インターフェースではありません。
 */
public sealed interface MouseActionInvoker permits Component {
    void invokeMouseAction();
}
