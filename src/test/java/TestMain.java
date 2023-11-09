import org.glcf2.Window;

public class TestMain {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Window window = new Window();

        window.start();

        window.update();

        window.close();
    }
}
