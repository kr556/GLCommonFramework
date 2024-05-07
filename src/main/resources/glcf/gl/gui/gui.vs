#version 430 core

uniform vec4 bound; // GUIの領域に収まるよう変換
uniform vec4 background; // 背景色

in vec3 pos;
in vec2 tex;

out vec4 vColor;
out vec2 vTex;

mat4 boundToMatrix() {
    float x = bound.x;
    float y = bound.y;
    float w = bound.z;
    float h = bound.w;
    return mat4(
        w, 0, 0, x,
        0, h, 0, y,
        0, 0, 1, 0,
        0, 0, 0, 1
    );
}

void main() {
    vColor = background;
    vTex = tex;

    mat4 boundMat = boundToMatrix();

    gl_Position = boundMat * vec4(pos.xyz, 1);
}
