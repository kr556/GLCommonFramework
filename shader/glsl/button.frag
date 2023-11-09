#version 120

uniform sampler2D sampler;
uniform float t;
uniform float m_in;
uniform float m_ck;

varying vec2 tex_coords;

void main(){
    vec4 tex = texture2D(sampler, tex_coords);

    gl_FragColor = vec4(vec3(tex.xyz + 0.2 * m_in), 1.0)
                    + (vec4(-0.1, -0.1, 0.1, 1.0) * m_ck);
}
