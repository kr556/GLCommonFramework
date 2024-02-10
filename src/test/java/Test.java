import org.linear.main.vector.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Test {
    int i;

    public static void main(final String[] args) throws Exception {
        Integer i = 0;


        System.out.println(getT().equals(i.getClass()));
    }

    public static Class<? extends Number> getT() {
        return Integer.class;
    }
}

class KTT_Java_C {
    public static final List<Integer> createdIDs = new ArrayList<>(50);

    private final String name;
    private final int id;

    public KTT_Java_C(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static int createID() {
        final int s = createdIDs.size();

        for (int i = 0; i <= s; i++) {
            if (!createdIDs.contains(i)) {
                createdIDs.add(i);
                return i;
            }
        }

        createdIDs.add(s);

        return 0;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + id + "}";
    }
}

record KTT_Java_R(String name, int id) {
    public static final List<Integer> createdIDs = new ArrayList<>(50);

    public static int createID() {
        final int s = createdIDs.size();

        for (int i = 0; i <= s; i++) {
            if (!createdIDs.contains(i)) {
                createdIDs.add(i);
                return i;
            }
        }

        createdIDs.add(s);

        return 0;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + id + "}";
    }
}
