#version 120

uniform sampler2D sampler;
uniform vec2 r;
uniform float time;
uniform int key;
uniform vec4 background;

varying vec4 tex_colors;
varying vec2 tex_coods;
varying vec3 tex_normal;

void main(void){
    vec4 color = tex_colors;
    //    vec4 color = texture2D(sampler, tex_coods);
    float r = color.x * time * 10;
    float g = color.y * time * 10;
    float b = color.z * time * 10;
    float a = color.w;
    color = vec4(vec3(sin(r * 0.4), sin(g * 0.5334), cos(b * 0.144)), a);

    gl_FragColor = tex_colors;
}