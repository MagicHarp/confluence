#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform int FogShape;
uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

out float vertexDistance;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 overlayColor;
out vec2 texCoord0;
out vec4 normal;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    vec2 light = clamp(vec2(UV2) / 16.0, vec2(0.0), vec2(15.0));
    ivec2 lo = ivec2(floor(light));
    ivec2 hi = min(lo + ivec2(1), ivec2(15));
    vec2 weight = fract(light);
    lightMapColor = mix(mix(texelFetch(Sampler2, lo, 0), texelFetch(Sampler2, ivec2(hi.x, lo.y), 0), weight.x), mix(texelFetch(Sampler2, ivec2(lo.x, hi.y), 0), texelFetch(Sampler2, hi, 0), weight.x), weight.y);
    overlayColor = texelFetch(Sampler1, UV1, 0);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
