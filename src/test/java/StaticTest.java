import org.glcf.main.*;
import org.glcf.main.Window;
import org.glcf.main.gui.GLColor;
import org.glcf.main.gui.RectGUI;

import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public final class StaticTest extends WindowMain {
    static {
        registerEntryPoint(new StaticTest());
    }

    @Override
    public void wmain(String[] args, Window window) {
        window.set().show();

        window.addUI(RectGUI.create(set -> set
                .pos(0, 0)
                .size(1, 1)
                .background(new GLColor(1, 1, 0, 1))));
    }
}
