#version 430 core

uniform mat4 local;
uniform mat4 world;

in vec4 lPos;
in vec4 lColor;

out vec4 wColor;

void main() {
    wColor = lColor;
    gl_Position = lPos;
}
