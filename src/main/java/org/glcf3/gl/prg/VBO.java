package org.glcf3.gl.prg;

import org.linear.main.vector.Vector;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;

public abstract class VBO implements PrograObject {
    protected final int id;
    protected int length;

    protected VBO(int id) {
        this.id = id;
    }

    public int size() {
        return length;
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public int getId() {
        return id;
    }

    public <E extends Number, V extends Vector<E, V>> void register(V[] data) {
        if (data.length == 0) return;

        E dataSamp = (E) data[0];
        final int dim = data[0].dimension();

        if (dataSamp.getClass() == getElementType()) {
            E[] arrData = (E[]) new Number[data.length * data[0].dimension()];
            E[] tmp = (E[]) new Number[dim];

            for (int dn = 0; dn < data.length; dn++) {
                data[dn].toArray(tmp);

                System.arraycopy(tmp, 0, arrData, dn * dim, dim);
            }

            register(arrData);
        }
    }

    protected void register(Number[] data) {
        length = data.length;
    }

    public void register(Buffer data) {
        length = data.limit();
    }

    public abstract Class<?> getElementType();

    static abstract class Float extends VBO {
        protected Float(int id) {
            super(id);
        }

        @Override
        public void register(Buffer data) {
            super.register(data);
            if (!(data instanceof FloatBuffer)) throw new ClassCastException();
            bind();
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        public final Class<? extends Number> getElementType() {
            return java.lang.Float.class;
        }

        public void register(float[] data) {
            length = data.length;
            if (data.length != 0) return;
            bind();
            glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        protected void register(Number[] data) {
            super.register(data);
            if (!(data[0] instanceof java.lang.Float)) throw new ClassCastException();

            float[] regArr = new float[data.length];
            java.lang.Float[] fa = (java.lang.Float[]) data;

            for (int i = 0; i < data.length; i++) {
                regArr[i] = fa[i];
            }

            register(regArr);
        }
    }

    static abstract class Int extends VBO {
        protected Int(int id) {
            super(id);
        }

        @Override
        public void register(Buffer data) {
            super.register(data);
            if (!(data instanceof IntBuffer)) throw new ClassCastException();
            bind();
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        public final Class<? extends Number> getElementType() {
            return Integer.class;
        }

        public void register(int[] data) {
            length = data.length;
            if (data.length != 0) return;
            bind();
            glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        protected void register(Number[] data) {
            super.register(data);
            if (!(data[0] instanceof java.lang.Integer)) throw new ClassCastException();

            int[] regArr = new int[data.length];
            java.lang.Integer[] fa = (java.lang.Integer[]) data;

            for (int i = 0; i < data.length; i++) {
                regArr[i] = fa[i];
            }

            register(regArr);
        }
    }

    static abstract class Double extends VBO {
        protected Double(int id) {
            super(id);
        }

        @Override
        public void register(Buffer data) {
            super.register(data);
            if (!(data instanceof DoubleBuffer)) throw new ClassCastException();
            bind();
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        public final Class<? extends Number> getElementType() {
            return java.lang.Double.class;
        }

        public void register(double[] data) {
            length = data.length;
            if (data.length != 0) return;
            bind();
            glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        protected void register(Number[] data) {
            super.register(data);
            if (!(data[0] instanceof java.lang.Double)) throw new ClassCastException();

            double[] regArr = new double[data.length];
            java.lang.Double[] fa = (java.lang.Double[]) data;

            for (int i = 0; i < data.length; i++) {
                regArr[i] = fa[i];
            }

            register(regArr);
        }
    }

    static abstract class Long extends VBO {
        protected Long(int id) {
            super(id);
        }

        @Override
        public void register(Buffer data) {
            super.register(data);
            if (!(data instanceof LongBuffer)) throw new ClassCastException();
            bind();
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        public final Class<? extends Number> getElementType() {
            return java.lang.Long.class;
        }

        public void register(long[] data) {
            length = data.length;
            if (data.length != 0) return;
            bind();
            glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
            unbind();
        }

        @Override
        protected void register(Number[] data) {
            super.register(data);
            if (!(data[0] instanceof java.lang.Long)) throw new ClassCastException();

            long[] regArr = new long[data.length];
            java.lang.Long[] fa = (java.lang.Long[]) data;

            for (int i = 0; i < data.length; i++) {
                regArr[i] = fa[i];
            }

            register(regArr);
        }
    }

    static abstract class Byte extends VBO {
        protected Byte(int id) {
            super(id);
        }

        @Override
        public final Class<? extends Number> getElementType() {
            return java.lang.Byte.class;
        }

        @Override
        public void register(Buffer data) {
            super.register(data);
            if (!(data instanceof ByteBuffer)) throw new ClassCastException();
            bind();
            glBufferData(GL_ARRAY_BUFFER, (ByteBuffer) data, GL_STATIC_DRAW);
            unbind();
        }

        public void register(byte[] data) {
            length = data.length;
            if (data.length != 0) return;
            register(ByteBuffer.wrap(data));
        }

        @Override
        protected void register(Number[] data) {
            super.register(data);
            if (!(data[0] instanceof java.lang.Byte)) throw new ClassCastException();

            byte[] regArr = new byte[data.length];
            java.lang.Byte[] fa = (java.lang.Byte[]) data;

            for (int i = 0; i < data.length; i++) {
                regArr[i] = fa[i];
            }

            register(regArr);
        }
    }
}
