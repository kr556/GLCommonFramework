package org.glcf2.component.glcomponent;

import org.glcf2.Shader;
import org.glcf2.models.Model;

@FunctionalInterface
public interface UniformSetter {
    void set(long win, double time , Shader model);
}
