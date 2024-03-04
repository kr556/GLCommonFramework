#version 150 core

uniform sampler2D samp;

in vec4 vColor;
in vec2 vTex;

out vec4 glColor;

void main() {
    glColor = texture(samp, vTex);
}
