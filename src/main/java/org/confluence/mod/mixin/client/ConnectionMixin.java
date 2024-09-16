package org.confluence.mod.mixin.client;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

/** 用来记录客户端收到的包的类型和数量
 * 当时用来调试动态群系巨量发包的问题的
 * 类先留着 万一以后还用得到呢 */
@Mixin(Connection.class)
public class ConnectionMixin {
    @Unique private static final Map<Object, Long> counter = new HashMap<>();
    @Inject(method = "genericsFtw",at = @At("HEAD"))
    private static void packet(Packet pPacket, PacketListener pListener, CallbackInfo ci){
        counter.putIfAbsent(pPacket.getClass(), 1L);
        counter.computeIfPresent(pPacket.getClass(), (k, v) -> v + 1);
    }
}
