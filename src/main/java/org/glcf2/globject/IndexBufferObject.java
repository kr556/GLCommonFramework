package org.glcf2.globject;

import java.nio.*;

import static org.lwjgl.opengl.GL20.*;

public sealed abstract class IndexBufferObject extends GLObjct {
    protected int ibo;
    protected int length;

    public static Float create(FloatBuffer buffer) {
        Float re = new Float();
        re.ibo = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Float create(float[] buffer) {
        Float re = new Float();
        re.ibo = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Double create(DoubleBuffer buffer) {
        Double re = new Double();
        re.ibo = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Double create(double[] buffer) {
        Double re = new Double();
        re.ibo = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Byte create(ByteBuffer buffer) {
        Byte re = new Byte();
        re.ibo = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Short create(ShortBuffer buffer) {
        Short re = new Short();
        re.ibo = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Short create(short[] buffer) {
        Short re = new Short();
        re.ibo = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Int create(IntBuffer buffer) {
        Int re = new Int();
        re.ibo = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Int create(int[] buffer) {
        Int re = new Int();
        re.ibo = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Long create(LongBuffer buffer) {
        Long re = new Long();
        re.ibo = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Long create(long[] buffer) {
        Long re = new Long();
        re.ibo = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    private IndexBufferObject() {}

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
    }

    public int id() {
        return ibo;
    }

    @Override
    protected void finalize() {}

    public int length() {
        return length;
    }

    public static final class Float extends IndexBufferObject {
        public void register(FloatBuffer buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        public void register(float[] buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    public static final class Double extends IndexBufferObject {
        public void register(DoubleBuffer buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        public void register(double[] buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    public static final class Byte extends IndexBufferObject {
        public void register(ByteBuffer buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    public static final class Int extends IndexBufferObject {
        public void register(IntBuffer buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        public void register(int[] buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    public static final class Short extends IndexBufferObject {
        public void register(ShortBuffer buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        public void register(short[] buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    public static final class Long extends IndexBufferObject {
        public void register(LongBuffer buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        public void register(long[] buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }
}
