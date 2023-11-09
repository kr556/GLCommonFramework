import org.glcf2.Texture;
import org.glcf2.Window;
import org.glcf2.io.ImageReader;
import org.glcf2.models.Model;
import org.glcf2.shaders.Shader;
import org.glcf2.globject.IndexBufferObject;
import org.glcf2.globject.VertexBufferObject;
import org.lwjgl.glfw.GLFW;

import java.awt.image.BufferedImage;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        Window window = new Window();
        window.start();

        var pos = VertexBufferObject.create(new float[]{
                -0.5f, 0.0f, 1f,
                0.0f, 0.5f, 1f,
                0.5f, 0.0f, 1f,
        });
        var col = VertexBufferObject.create(new float[]{
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 0f, 1f, 1f,
        });
        var idx = IndexBufferObject.create(new int[]{
                0, 1, 2,
        });

        Shader prg = Shader.readFile("test");

        BufferedImage texImg = ImageReader.read(new File("その他/img_1.png"), e -> null);
        Texture texture = new Texture(texImg);

        Model model = new Model();
        model.setPrgogram(prg);
        model.setVerties(pos);
        model.setIndicies(idx);
        model.setTexture(texture);
        model.setColor(col);

        prg.setUniformm("r", (float) texImg.getWidth(), (float) texImg.getHeight());
        window.setFrameTime(0.000);

        window.setFrameAction(l -> {
            model.drawing();
            prg.setUniform("t", (float) GLFW.glfwGetTime());
            System.out.println(1 / window.getFrameTime());
        });

        window.update();
        window.close();

    }
}
