#version 150 core

in vec3 pos;
in vec4 color;

out vec4 vColor;

void main() {
    vColor = color;

    gl_Position = vec4(pos, 1);
}
