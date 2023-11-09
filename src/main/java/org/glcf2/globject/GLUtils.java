package org.glcf2.globject;

import java.util.List;

public final class GLUtils {
    private GLUtils() {}

    public static int createId(int[] ids) {
        if (ids.length == 0) return 0;

        for (int i = 0; true; i++) {
            if (i != ids[i]) return i;
        }
    }

    public static int createId(List<Integer> ids) {
        if (ids.size() == 0) return 0;

        for (int i = 0; true; i++) {
            if (!ids.contains(i)) return i;
        }
    }
}
