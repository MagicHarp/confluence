package org.confluence.mod.mixin;

import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @Inject(method = "getDestroyProgress", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void checkPhase(BlockState state, Player player, BlockGetter getter, BlockPos pos, CallbackInfoReturnable<Float> cir, float f, int i) {
        if (RevelationRegistry.isVisibleTo(state, player)) return;
        BlockState target = RevelationRegistry.getCloak(state);
        if (target == null) return;
        cir.setReturnValue(player.getDigSpeed(target, pos) / f / (float) i);
    }
}
