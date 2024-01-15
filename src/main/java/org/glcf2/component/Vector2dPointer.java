package org.glcf2.component;

import org.linear.main.vector.AbsVector;
import org.linear.main.vector.Vector2d;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.DoubleBuffer;

import static java.lang.Math.*;

public final class Vector2dPointer extends AbsVector<Double, Vector2dPointer> {
    public final DoubleBuffer x;
    public final DoubleBuffer y;

    public Vector2dPointer(Vector2d copy) {
        this.x = BufferUtils.createDoubleBuffer(1);
        this.y = BufferUtils.createDoubleBuffer(1);
        x_(copy.x);
        y_(copy.y);
    }

    public Vector2dPointer(double x, double y) {
        this.x = BufferUtils.createDoubleBuffer(1);
        this.y = BufferUtils.createDoubleBuffer(1);
        x_(x);
        y_(y);
    }

    public Vector2dPointer(Vector2dPointer copy) {
        x = BufferUtils.createDoubleBuffer(1);
        y = BufferUtils.createDoubleBuffer(1);
        set(copy);
    }

    public Vector2dPointer(DoubleBuffer x, DoubleBuffer y) {
        this.x = x;
        this.y = y;
    }

    public Vector2dPointer() {
        this.x = BufferUtils.createDoubleBuffer(1);
        this.y = BufferUtils.createDoubleBuffer(1);
    }

    @Override
    public Vector2dPointer clone() {
        return new Vector2dPointer(x, y);
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public Buffer get(Buffer buffer) {
        if (buffer instanceof DoubleBuffer re) {
            if (buffer.limit() >= 2) throw new ArrayIndexOutOfBoundsException();
            ((DoubleBuffer) buffer).put(0, x());
            ((DoubleBuffer) buffer).put(1, y());
            return re;
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public void set(int i, Double d) {
        switch (i) {
            case 0 -> x_(d);
            case 1 -> y_(d);
        }
    }

    public void set(double x, double y) {
        x_(x);
        y_(y);
    }

    @Override
    public boolean equals(Vector2dPointer vector2iPointer) {
        return vector2iPointer.x.equals(x) &&
               vector2iPointer.y.equals(y);
    }

    @Override
    public boolean isNaN() {
        return Double.isNaN(x()) &&
               Double.isNaN(y());
    }

    @Override
    public Double get(int i) {
        return switch (i) {
            case 0 -> x();
            case 1 -> y();
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public Double[] toArray() {
        return new Double[]{x(), y()};
    }

    @Override
    public Double[] toArray(Double[] integers) {
        integers[0] = x();
        integers[1] = y();
        return new Double[0];
    }

    @Override
    public double distance(Vector2dPointer value) {
        double x = (value.x() - this.x());
        double y = (value.y() - this.y());
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public double len() {
        return sqrt(x() * x() + y() * y());
    }

    @Override
    public double angle(Vector2dPointer value) {
        return Math.acos((double)this.y() / this.len());
    }

    @Override
    public double dot(Vector2dPointer value) {
        return this.len() * value.len() * Math.cos(this.angle(value));
    }

    @Override
    public Vector2dPointer nomalize() {
        double len = this.len();
        this.x_((x() / len));
        this.y_((y() / len));
        return this;
    }

    @Override
    public Vector2dPointer nomalize(Vector2dPointer vector2iPointer) {
        vector2iPointer.set(this);
        return vector2iPointer.nomalize();
    }

    public Vector2d toVetor2d() {
        return new Vector2d(x(), y());
    }

    public void set(Vector2dPointer vector2iPointer) {
        this.x_(vector2iPointer.x());
        this.y_(vector2iPointer.y());
    }

    public void x_(double i) {
        x.put(0, i);
    }

    public void y_(double i) {
        y.put(0, i);
    }

    public double x() {
        return x.get(0);
    }

    public double y() {
        return y.get(0);
    }

    public void set(Vector2d vector2d) {
        x_(vector2d.x);
        y_(vector2d.y);
    }
}
