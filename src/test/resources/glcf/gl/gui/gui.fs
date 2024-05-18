#version 430 core

uniform sampler2D samp;

in vec4 vColor;
in vec2 vTex;

out vec4 glColor;

void main() {
    glColor = vec4(vColor) * texture2D(samp, vTex);
}