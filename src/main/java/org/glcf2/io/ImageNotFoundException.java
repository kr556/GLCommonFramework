package org.glcf2.io;

import java.io.IOException;

public class ImageNotFoundException extends IOException {
    public ImageNotFoundException() {
        super();
    }

    public ImageNotFoundException(String messege) {
        super(messege);
    }
}
