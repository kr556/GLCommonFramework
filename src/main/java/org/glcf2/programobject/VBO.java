package org.glcf2.programobject;

import org.linear.main.vector.DoubleVector;
import org.linear.main.vector.FloatVector;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;

public abstract class VBO extends ProgramObjct {
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

    public static Float create(FloatVector<?>[] vectors, int dimension) {
        Float re = new Float();
        re.id = glGenBuffers();
        re.length = vectors.length * dimension;
        re.register(vectors, dimension);

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

    public static Double create(DoubleVector<?>[] vectors, int dimension) {
        Double re = new Double();
        re.id = glGenBuffers();
        re.length = vectors.length * dimension;
        re.register(vectors, dimension);

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

    protected VBO() {
        id = -1;
        length = 0;
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public int length() {
        return length;
    }

    public int id() {
        return id;
    }

    @Override
    protected void finalize() {}

    public static class Float extends VBO {
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

        public void register(FloatVector<?>[] vectors, int dimension) {
            float[] tmpV = new float[vectors[0].dimension()];
            float[] tmp = new float[dimension * vectors.length];
            for (int i = 0; i < vectors.length; i++) {
                vectors[i].toArray(tmpV);

                System.arraycopy(tmpV, 0, tmp, i * dimension, dimension);
            }
            register(tmp);
        }
    }

    public static class Double extends VBO {
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

        public void register(DoubleVector<?>[] vectors, int dimension) {
            double[] tmpV = new double[vectors[0].dimension()];
            double[] tmp = new double[dimension * vectors.length];
            for (int i = 0; i < vectors.length; i++) {
                vectors[i].toArray(tmpV);

                System.arraycopy(tmpV, 0, tmp, i * dimension, dimension);
            }
            register(tmp);
        }
    }

    public static class Byte extends VBO {
        public void register(ByteBuffer buffer) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public static class Int extends VBO {
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

    public static class Short extends VBO {
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

    public static class Long extends VBO {
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
