#version 420

out vec4 fragColor;
in vec2 texCoord;

uniform sampler2D att;
uniform int hor;

void main()
{
    float weight[5] = float[] (0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);
    vec2 tex_offset = 1.0 / vec2(200,200); // gets size of single texel
    vec3 result = texture(att, texCoord).rgb * weight[0]; // current fragment's contribution
    if(hor>0)
    {
        for(int i = 1; i < 5; ++i)
        {
            result += texture(att, texCoord + vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
            result += texture(att, texCoord - vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
        }
    }
    else
    {
        for(int i = 1; i < 5; ++i)
        {
            result += texture(att, texCoord + vec2(0.0, tex_offset.y * i)).rgb * weight[i];
            result += texture(att, texCoord - vec2(0.0, tex_offset.y * i)).rgb * weight[i];
        }
    }
    fragColor = vec4(result, texture(att, texCoord).a);
}
