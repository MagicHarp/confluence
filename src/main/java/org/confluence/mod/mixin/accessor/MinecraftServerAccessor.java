package org.confluence.mod.mixin.accessor;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(MinecraftServer.class)
public interface MinecraftServerAccessor {
    @Accessor
    List<Runnable> getTickables();
}
