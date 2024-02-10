package org.glcf3;

import org.glcf3.gl.prg.PrograObject;

import java.awt.image.BufferedImage;

public interface Texture extends PrograObject {
    BufferedImage getImage();

    void bind(int samplerId);

    int getWidth();

    int getHeight();
}
