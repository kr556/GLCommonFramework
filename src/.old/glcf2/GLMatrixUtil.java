package org.glcf2;

import org.linear.main.matrix.Matrix4f;

public final class GLMatrixUtil {
    private GLMatrixUtil() {}
    public static Matrix4f createPerspective4f(float left, float right,
                                               float bottom, float top,
                                               float near, float far) {
        return new Matrix4f(
//                1, 0, 0, 0,
//                0, 1, 0, 0,
//                0, 0, 1, 0,
//                0, 0, 0, 1


                2 / (right - left)          , 0                         , 0                     , -(right + left) / (right - left)  ,
                0                           , 2 / (top - bottom)        , 0                     , -(top + bottom) / (top - bottom)  ,
                0                           , 0                         , -2 / (far - near)     , -(far + near) / (far - near)      ,
                0                           , 0                         , 0                     , 1

//                2 * near / (right - left)   , 0                         , (right + left) / (right - left)   ,               0,
//                0                           , 2 * near / (top - bottom) , (top + bottom) / (top - bottom)   ,               0,
//                0                           , 0                         , -(far + near) / (far - near)      , -2 * far * near / (far - near),
//                0                           , 0                         , -1                                , 0
        ).transpose();
    }
}
