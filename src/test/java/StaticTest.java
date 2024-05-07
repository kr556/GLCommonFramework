import org.glcf.main.*;
import org.glcf.main.Window;
import org.glcf.main.gui.GUIShader;
import org.linear.main.vector.Vector4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public final class StaticTest extends WindowMain {
    static {
        registEntryPoint(new StaticTest());
    }
//    public static void main(String[] args) {
//
//    }
    @Override
    public void wmain(String[] args, Window window) {
        window.set().hide();
        VFShader s = Test000.getVFShader("d");
        System.out.println(s.getVertexSrc().formatLine().getVariable());
    }
}
