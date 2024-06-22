#version 150

vec2 hash22(vec2 p){
    p = vec2( dot(p,vec2(127.1,311.7)),
              dot(p,vec2(269.5,183.3)));
    return -1.0 + 2.0 * fract(sin(p)*43758.5453123);
}

vec2 hash(vec2 p)//不用sin的更好
{
    vec3 p3 = fract(vec3(p.xyx) * vec3(.1031, .1030, .0973));
    p3 += dot(p3, p3.yzx+19.19);
    return -1. + 2.*fract((p3.xx+p3.yz)*p3.zy);
}

float perlin_noise(vec2 p){
    vec2 pi = floor(p);
    vec2 pf = p - pi;
    
    //一个类似smoothstep的函数，叫Hermit插值函数，也叫S曲线：S(x) = -2 x^3 + 3 x^2
    //利用Hermit插值特性：可以在保证函数输出的基础上保证插值函数的导数在插值点上为0，这样就提供了平滑性
    vec2 w = pf * pf * (3.0 - 2.0 * pf);

    return mix(mix(dot(hash22(pi + vec2(0.0, 0.0)), pf - vec2(0.0, 0.0)), 
                   dot(hash22(pi + vec2(1.0, 0.0)), pf - vec2(1.0, 0.0)), w.x), 
               mix(dot(hash22(pi + vec2(0.0, 1.0)), pf - vec2(0.0, 1.0)), 
                   dot(hash22(pi + vec2(1.0, 1.0)), pf - vec2(1.0, 1.0)), w.x),
               w.y);
}