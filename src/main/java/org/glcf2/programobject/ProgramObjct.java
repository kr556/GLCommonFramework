package org.glcf2.programobject;

public abstract class ProgramObjct {
    public abstract int id();
    public abstract void bind();
    public abstract int length();

    @SuppressWarnings("deprecation")
    @Override
    protected abstract void finalize() throws Throwable;
}
