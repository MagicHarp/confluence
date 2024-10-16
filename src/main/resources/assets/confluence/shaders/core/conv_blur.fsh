#version 420
in vec2 texCoord;

//layout(binding = 0)
//layout(binding = 1)
uniform sampler2D att;
uniform sampler2D ori;
uniform int offs;

out vec4 fragColor;

void main(){


    //卷积模糊
//    if( length(texture(att, texCoord ).rgb)>0){
//        fragColor =  texture(ori, texCoord);
//        fragColor =  texture(att, texCoord);
//    }else{
        float offset = offs/ 512.0;
        vec4 color1 = vec4(0.0);
        for(int i = -1; i <=1; i++){
            for(int j = -1; j <= 1; j++){
                color1 += texture(att, texCoord + vec2(float(i) * offset, float(j) * offset));
            }
        }
        color1 /= 9;
        fragColor =  texture(ori, texCoord) + color1;
//    }
}
