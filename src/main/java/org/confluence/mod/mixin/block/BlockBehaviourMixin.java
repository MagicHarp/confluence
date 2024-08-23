package org.confluence.mod.mixin.block;

import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Inject(method = "getDestroyProgress", at = @At("HEAD"), cancellable = true)
    private void checkPhase(BlockState state, Player player, BlockGetter getter, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (RevelationRegistry.isVisibleTo(state, player)) return;
        BlockState target = RevelationRegistry.getCloak(state);
        if (target == null) return;
        float i = ForgeHooks.isCorrectToolForDrops(target, player) ? 30.0F : 100.0F;
        cir.setReturnValue(player.getDigSpeed(target, pos) / target.getDestroySpeed(getter, pos) / i);
    }
}
