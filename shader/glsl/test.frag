#version 120

uniform sampler2D sampler;
uniform vec2 r;
uniform float t;

varying vec4 tex_colors;
varying vec2 tex_coods;

void main(void){
    vec4 color = tex_colors;

    gl_FragColor = color;
}