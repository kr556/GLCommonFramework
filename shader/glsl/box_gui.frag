#version 120

uniform sampler2D sampler;
uniform float t;

varying vec2 tex_coords;

void main(){
    vec4 tex = texture2D(sampler, tex_coords);

    gl_FragColor = tex;
}
