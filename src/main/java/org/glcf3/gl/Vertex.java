package org.glcf3.gl;

import org.linear.main.Linear;
import org.linear.main.vector.Vector4f;

import java.nio.Buffer;
import java.nio.FloatBuffer;

public class Vertex extends Vector4f {
    private Linear<?, ?>[] attributes;

    @SafeVarargs
    public <L extends Linear<? , L>> Vertex(L... attributesParameter) {
        attributes = attributesParameter;
    }

    public Vertex(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    public Vertex(Vector4f copy) {
        super(copy);
    }

    public <L extends Linear<?, L>> L[] getAttributes() {
        return (L[]) attributes;
    }

    @Override
    public Buffer get(Buffer pointer) {
        if (!(pointer instanceof
                FloatBuffer bf)) throw new UnsupportedOperationException();
        bf.put(0, x);
        bf.put(1, y);
        bf.put(2, z);
        bf.put(3, w);
        return bf;
    }
}
