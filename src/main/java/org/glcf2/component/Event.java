package org.glcf2.component;

interface Event {
    int NULL = -1;

    /**
     * 初期化処理。以下をイベントが発生するより前に呼び出してください。このメソッドを機器ごとに単体で呼び出す必要はほとんどありません。
     * {@link Events#init() }
     */
    void init();

    /**
     * このイベントの状態を必要に応じて更新します。
     */
    void update();

    /**
     * このイベントの状態を読み込む前にリセットします。
     */
    void reset();

    /**
     * イベントが発生したかどうかを返します。
     * @return イベントが発生した場合true
     */
    boolean invoked();

    static boolean isNull(int i) {
        return i == NULL;
    }

    /**
     * イベント名、またはボタン名を環境ごとに設定できます。
     * @param <R>
     */
    interface EventID<R extends Number> {
        R press();
        R release();
    }
}
