#version 120

uniform mat4 c_m4in;

uniform float time;
uniform mat3 m3in;
uniform float frag;
uniform int key;
uniform int layer;

attribute vec3 vertices;
attribute vec4 color;
attribute vec2 textures;
attribute vec3 normal;

varying vec4 tex_colors;
varying vec2 tex_coods;
varying vec3 tex_normal;

void main() {
    tex_colors = color;
    gl_Position = vec4(vertices, 1) * c_m4in;
}