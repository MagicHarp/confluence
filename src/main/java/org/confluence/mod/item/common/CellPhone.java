package org.confluence.mod.item.common;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.informational.*;
import org.jetbrains.annotations.NotNull;

public class CellPhone extends Item implements ICompass, IDepthMeter, IDPSMeter, IFishermansPocketGuide,
    ILifeFormAnalyzer, IMetalDetector, IRadar, ISextant, IStopwatch, ITallyCounter, IWatch, IWeatherRadio {
    public CellPhone() {
        super(new Properties().stacksTo(1).rarity(ModRarity.LIME).fireResistant());
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        level.playSound(player, player.getOnPos(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1, 1);
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 20;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living) {
        if (level.isClientSide) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack);
        } else if (living instanceof ServerPlayer serverPlayer) {
            BlockPos pos = serverPlayer.getRespawnPosition();
            if (pos == null) {
                ServerLevel serverLevel = serverPlayer.server.getLevel(Level.OVERWORLD);
                if (serverLevel == null) serverLevel = (ServerLevel) level;
                LevelData data = serverLevel.getLevelData();
                serverPlayer.teleportTo(data.getXSpawn(), data.getYSpawn(), data.getXSpawn());
            } else {
                serverPlayer.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            }
            serverPlayer.getCooldowns().addCooldown(this, 5);
        }
        return itemStack;
    }
}
