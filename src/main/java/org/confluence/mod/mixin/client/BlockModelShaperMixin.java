package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.block.EchoBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModelShaper.class)
public class BlockModelShaperMixin {
    @Inject(method = "getTexture", at = @At("HEAD"))
    private void ifEchoBlock(BlockState blockState, Level level, BlockPos pos, CallbackInfoReturnable<TextureAtlasSprite> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        if (blockState.is(ConfluenceBlocks.ECHO_BLOCK.get()) && player.getItemBySlot(EquipmentSlot.HEAD).is(Items.IRON_HELMET)) {
            blockState = blockState.setValue(EchoBlock.VISIBLE, true);
        }
    }
}
