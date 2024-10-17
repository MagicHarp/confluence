#version 420
in vec2 texCoord;

uniform sampler2D att;
uniform sampler2D ori;

out vec4 fragColor;

void main(){
    //if(texture(att, texCoord).a < 0.01) discard;
    fragColor = texture(ori, texCoord) + texture(att, texCoord);
}
