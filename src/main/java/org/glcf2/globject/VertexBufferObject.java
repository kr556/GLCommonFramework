package org.glcf2.globject;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;

public abstract class VertexBufferObject extends GLObjct{
    protected int id;
    protected int length;

    public static Float create(FloatBuffer buffer) {
        Float re = new Float();
        re.id = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Float create(float[] buffer) {
        Float re = new Float();
        re.id = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Double create(DoubleBuffer buffer) {
        Double re = new Double();
        re.id = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Double create(double[] buffer) {
        Double re = new Double();
        re.id = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Byte create(ByteBuffer buffer) {
        Byte re = new Byte();
        re.id = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Short create(ShortBuffer buffer) {
        Short re = new Short();
        re.id = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Short create(short[] buffer) {
        Short re = new Short();
        re.id = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Int create(IntBuffer buffer) {
        Int re = new Int();
        re.id = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Int create(int[] buffer) {
        Int re = new Int();
        re.id = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    public static Long create(LongBuffer buffer) {
        Long re = new Long();
        re.id = glGenBuffers();
        re.length = buffer.limit();
        re.register(buffer);

        return re;
    }

    public static Long create(long[] buffer) {
        Long re = new Long();
        re.id = glGenBuffers();
        re.length = buffer.length;
        re.register(buffer);

        return re;
    }

    protected VertexBufferObject() {
        id = -1;
        length = 0;
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public int id() {
        return id;
    }

    @Override
    protected void finalize() {}

    public static class Float extends VertexBufferObject {
        public void register(FloatBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        public void register(float[] buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public static class Double extends VertexBufferObject {
        public void register(DoubleBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        public void register(double[] buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public static class Byte extends VertexBufferObject {
        public void register(ByteBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public static class Int extends VertexBufferObject {
        public void register(IntBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        public void register(int[] buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public static class Short extends VertexBufferObject {
        public void register(ShortBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        public void register(short[] buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public static class Long extends VertexBufferObject {
        public void register(LongBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        public void register(long[] buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }
}
