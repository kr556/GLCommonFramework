package org.glcf2.component;

import org.linear.main.vector.AbsVector;
import org.linear.main.vector.Vector2d;
import org.linear.main.vector.Vector2i;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.IntBuffer;

import static java.lang.Math.*;

public final class Vector2iPointer extends AbsVector<Integer, Vector2iPointer> {
    public final IntBuffer x;
    public final IntBuffer y;

    public Vector2iPointer(Vector2i copy) {
        this.x = BufferUtils.createIntBuffer(1);
        this.y = BufferUtils.createIntBuffer(1);
        x_(copy.x);
        y_(copy.y);
    }

    public Vector2iPointer(int x, int y) {
        this.x = BufferUtils.createIntBuffer(1);
        this.y = BufferUtils.createIntBuffer(1);
        x_(x);
        y_(y);
    }

    public Vector2iPointer(Vector2iPointer copy) {
        x = BufferUtils.createIntBuffer(1);
        y = BufferUtils.createIntBuffer(1);
        set(copy);
    }

    public Vector2iPointer(IntBuffer x, IntBuffer y) {
        this.x = x;
        this.y = y;
    }

    public Vector2iPointer() {
        this.x = BufferUtils.createIntBuffer(1);
        this.y = BufferUtils.createIntBuffer(1);
    }

    @Override
    public Vector2iPointer clone() {
        return new Vector2iPointer(x, y);
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public Buffer get(Buffer buffer) {
        if (buffer instanceof IntBuffer re) {
            if (buffer.limit() >= 2) throw new ArrayIndexOutOfBoundsException();
            ((IntBuffer) buffer).put(0, x());
            ((IntBuffer) buffer).put(1, y());
            return re;
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public void set(int i, Integer integer) {
        switch (i) {
            case 0 -> x_(integer);
            case 1 -> y_(integer);
        }
    }

    public void set(int x, int y) {
        x_(x);
        y_(y);
    }

    @Override
    public boolean equals(Vector2iPointer vector2iPointer) {
        return vector2iPointer.x.equals(x) &&
               vector2iPointer.y.equals(y);
    }

    @Override
    public boolean isNaN() {
        return false;
    }

    @Override
    public Integer get(int i) {
        return switch (i) {
            case 0 -> x();
            case 1 -> y();
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public Integer[] toArray() {
        return new Integer[]{x(), y()};
    }

    @Override
    public Integer[] toArray(Integer[] integers) {
        integers[0] = x();
        integers[1] = y();
        return new Integer[0];
    }

    @Override
    public double distance(Vector2iPointer value) {
        double x = (value.x() - this.x());
        double y = (value.y() - this.y());
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public double len() {
        return sqrt(x() * x() + y() * y());
    }

    @Override
    public double angle(Vector2iPointer value) {
        return Math.acos((double)this.y() / this.len());
    }

    @Override
    public double dot(Vector2iPointer value) {
        return this.len() * value.len() * Math.cos(this.angle(value));
    }

    @Override
    public Vector2iPointer nomalize() {
        double len = this.len();
        this.x_((int) (x() / len));
        this.y_((int) (y() / len));
        return this;
    }

    @Override
    public Vector2iPointer nomalize(Vector2iPointer vector2iPointer) {
        vector2iPointer.set(this);
        return vector2iPointer.nomalize();
    }

    public Vector2i toVector2i() {
        return new Vector2i(x(), y());
    }

    public Vector2d toVector2d() {
        return new Vector2d(x(), y());
    }

    private void set(Vector2iPointer vector2iPointer) {
        this.x_(vector2iPointer.x());
        this.y_(vector2iPointer.y());
    }

    public void set(Vector2i vector2i) {
        x_(vector2i.x);
        y_(vector2i.y);
    }

    public void x_(int i) {
        x.put(0, i);
    }

    public void y_(int i) {
        y.put(0, i);
    }

    public int x() {
        return x.get(0);
    }

    public int y() {
        return y.get(0);
    }
}
