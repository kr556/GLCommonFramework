#version 120

uniform float t;

attribute vec3 vertices;
attribute vec4 color;
attribute vec2 textures;

varying vec4 tex_colors;
varying vec2 tex_coods;

void main() {
    tex_colors = color;
    tex_coods = textures;
    gl_Position = vec4(vertices, 1);
}