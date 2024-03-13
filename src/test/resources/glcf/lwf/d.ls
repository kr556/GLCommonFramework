#version 430 core

uniform mat4 local;
uniform mat4 world;

in vec3 pos;
in vec4 color;

out vec4 lPos;
out vec4 lColor;

void main() {
    lColor = color;
    normalize(1);
    lPos = world * vec4((local * vec4(pos, 1.0)));
}
