package org.glcf2.io;

/**
 * Store action of when catch an exception. When "e = null" this action same "catch(Exception ignored)".
 * Can set the return value when throw exception.
 */
public interface ExceptionCatcher<R> {
    R invoke(Exception e);
}