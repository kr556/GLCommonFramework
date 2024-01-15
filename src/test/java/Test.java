import static org.glcf2.FragUtil32.*;
import static org.glcf2.component.MouseEvent.MOUSE_PRESS;
import static org.glcf2.component.MouseEvent.MOUSE_IN;

public class Test {
    public static void main(final String[] args) throws Exception {
        int frag = MOUSE_PRESS | MOUSE_IN;

        System.out.println(fragIs(frag, MOUSE_PRESS));
        System.out.println(fragIs(frag, MOUSE_IN));

        frag = fragSetFalse(frag, MOUSE_PRESS);
        frag = fragSetFalse(frag, MOUSE_IN);

        frag = fragSetTrue(frag, MOUSE_PRESS);
        frag = fragSetTrue(frag, MOUSE_IN);

        System.out.println(fragIs(frag, MOUSE_PRESS));
        System.out.println(fragIs(frag, MOUSE_IN));


    }
}
