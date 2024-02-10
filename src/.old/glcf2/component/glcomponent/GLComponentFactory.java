package org.glcf2.component.glcomponent;

import org.glcf2.component.Component;
import org.linear.main.vector.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class GLComponentFactory {
    private GLComponentFactory() {
    }

    public static GLWindow createWindow(GLWindow.DrawingsType drawingType, Vector2d windowSize, String title, long monitor, long share, int initialDrawingsSize) {
        if (!Objects.nonNull(drawingType)) throw new NullPointerException();
        return switch (drawingType) {
            case LIST -> new GLWindow(windowSize, title, monitor, share) {
                private List<Component> components = new ArrayList<>(initialDrawingsSize);

                {drawingType_ = drawingType;}

                @Override
                public void add(Component[] parts) {
                    components.addAll(Arrays.asList(parts));
                }

                @Override
                public void add(Component part) {
                    components.add(part);
                }

                @Override
                public Component get(int index) {
                    return components.get(index);
                }

                @Override
                public Component[] getAll() {
                    return components.toArray(new Component[0]);
                }

                @Override
                public void remove(Predicate<Component> filter) {
                    components = components.stream().filter(filter).toList();
                }

                @Override
                public void removeAll() {
                    components.clear();
                }
            };
            case ARRAY -> new GLWindow(windowSize, title, monitor, share) {
                private final Component[] components = new Component[initialDrawingsSize];

                {drawingType_ = drawingType;}

                @Override
                public void add(Component[] parts) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void add(Component part) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Component get(int index) {
                    return components[index];
                }

                @Override
                public Component[] getAll() {
                    return components;
                }

                @Override
                public void remove(Predicate<Component> filter) {
                    for (int i = 0; i < components.length; i++) {
                        if (filter.test(components[i])) {
                            components[i] = null;
                        }
                    }
                }

                @Override
                public void removeAll() {
                    Arrays.fill(components, null);
                }
            };
        };
    }
}
