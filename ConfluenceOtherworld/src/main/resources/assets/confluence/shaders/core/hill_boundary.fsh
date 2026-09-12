#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float Time;

in vec2 texCoord0;
in vec4 vertexColor;
out vec4 fragColor;

void main() {
    vec4 first = texture(Sampler0, fract(texCoord0 + vec2(0.0, Time * 0.4))) * 1.2;
    vec4 second = texture(Sampler0, fract(texCoord0 + vec2(Time * 0.24, Time * 0.96))) * 0.8;
    vec4 fire = pow(max((first + second) * 0.5 * vertexColor * ColorModulator, vec4(0.0)), vec4(1.5));
    fragColor = fire * (1.0 - clamp(pow(texCoord0.y, 1.2), 0.0, 1.0)) * 2.0;
    if (fragColor.a < 0.01) discard;
}
