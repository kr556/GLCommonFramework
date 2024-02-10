#version 120

uniform float time;
uniform mat3 m3in;
uniform mat4 m4in;
uniform mat4 pm4in;
uniform float frag;
uniform int key;

attribute vec3 vertices;
attribute vec4 color;
attribute vec2 textures;
attribute vec3 normal;

varying vec4 tex_colors;
varying vec2 tex_coods;
varying vec3 tex_normal;

void main() {
    float r = color.x * time * 10;
    float g = color.y * time * 10;
    float b = color.z * time * 10;
    float a = color.w;

    tex_colors = color;
    gl_Position = vec4(vertices, 1) * m4in;
}