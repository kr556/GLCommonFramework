#version 150 core

uniform mat4 local;
uniform mat4 world;

in vec4 pos;
in vec4 color;

out vec4 vColor;

void main() {
    vColor = color;
    gl_Position = world * vec4((local * pos).xyz, 1);
}
