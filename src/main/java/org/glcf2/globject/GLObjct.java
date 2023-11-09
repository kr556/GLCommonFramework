package org.glcf2.globject;

public abstract class GLObjct {
    public abstract int id();
    public abstract void bind();

    @SuppressWarnings("deprecation")
    @Override
    protected abstract void finalize() throws Throwable;
}
