#version 150 core

in vec4 vColor;

out vec4 glColor;

void main() {
    glColor = vec4(vColor);
}