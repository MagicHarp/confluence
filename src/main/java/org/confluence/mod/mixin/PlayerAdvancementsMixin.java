package org.confluence.mod.mixin;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import org.confluence.mod.Confluence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancements.class)
public abstract class PlayerAdvancementsMixin {
    @Shadow
    public abstract AdvancementProgress getOrStartProgress(Advancement p_135997_);

    @Shadow
    private ServerPlayer player;

    @Inject(method = "award", at = @At("HEAD"), cancellable = true)
    private void checkDone(Advancement advancement, String criterion, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof FakePlayer) return;
        if (Confluence.REQUIRE_PARENT_DONE.contains(advancement.getId())) {
            Advancement parent = advancement.getParent();
            if (parent == null) return;
            if (!getOrStartProgress(parent).isDone()) {
                cir.setReturnValue(false);
            }
        }
    }
}
