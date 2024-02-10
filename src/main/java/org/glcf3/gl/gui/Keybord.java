package org.glcf3.gl.gui;

public interface Keybord extends Event {

    default int getKeys(int index) {
        return getKeyEvent(index).getKey();
    }

    default int getScancodes(int index) {
        return getKeyEvent(index).getScancode();
    }

    default int getActions(int index) {
        return getKeyEvent(index).getEvent();
    }

    default int getMods(int index) {
        return getKeyEvent(index).getMods();
    }

    int getEventLenth();

    KeybordTemp getKeyEvent(int index);

    class KeybordTemp {
        public int key;
        public int scancode;
        public int action;
        public int mods;

        public void reset() {
            key = -1;
            scancode = -1;
            action = -1;
            mods = -1;
        }

        public boolean invoked() {
            return !Event.isNull(key) ||
                   !Event.isNull(scancode) ||
                   !Event.isNull(action) ||
                   !Event.isNull(mods);
        }

        public int getKey() {
            return key;
        }

        public int getScancode() {
            return scancode;
        }

        public int getEvent() {
            return action;
        }

        public int getMods() {
            return mods;
        }
    }
}
