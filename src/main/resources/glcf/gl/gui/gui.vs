#version 430 core

in vec3 pos;
in vec4 color;
in vec2 tex;

out vec4 vColor;
out vec2 vTex;

void main() {
    vColor = color;
    vTex = tex;
    gl_Position = vec4((pos).xyz, 1);
}
