package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.particle.opt.CurrentDustOptions;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.SpeedBootsNBTPacketC2S;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class BaseSpeedBoots extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("EE6FAFF5-A69D-6101-F82A-93E55A01F65E");
    public static final Component TOOLTIP = Component.translatable("curios.tooltip.speed_boots");

    public BaseSpeedBoots(Rarity rarity) {
        super(rarity);
    }

    public BaseSpeedBoots() {
        super();
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return EMPTY_TOOLTIP;
    }
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        speedUp(slotContext, stack.getOrCreateTag(), 1, 40);
    }

    public void spawnParticles(Level level, Vec3 vec3) {
        int rand = level.getRandom().nextInt(3, 5);
        double particleRandX = (double) (level.getRandom().nextInt(100, 300) - 200) / 1000;
        double particleRandY = (double) (level.getRandom().nextInt(100, 300) - 200) / 1000;
        double particleRandZ = (double) (level.getRandom().nextInt(100, 300) - 200) / 1000;
        CurrentDustOptions options = new CurrentDustOptions(getParticleColorStart(), getParticleColorEnd(), 1.2F);
        System.out.println(options.getColor());
        for (int i = 0; i < rand; ++i) {
            level.addParticle(options, vec3.x + particleRandX , vec3.y + particleRandY, vec3.z + particleRandZ, 0, 0, 0);
        }
    }
    private static final Vector3f COLOR = Vec3.fromRGB24(0xFFFFFF).toVector3f();
    public Vector3f getParticleColorStart() {
        return COLOR;
    }
    public Vector3f getParticleColorEnd() {
        return COLOR;
    }
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        stack.getOrCreateTag().putInt("speed", 0);
    }

    protected void speedUp(SlotContext slotContext, CompoundTag nbt, int addition, int max) {
        LivingEntity living = slotContext.entity();
        if (living.hasEffect(ModEffects.STONED.get())) return;
        if (living instanceof Player player && player.isLocalPlayer()) {
            int speed = nbt.getInt("speed");
            if (player.onGround() && player.zza > 0) {
                int actually = Math.min(max - speed, addition);
                if (actually > 0) {
                    NetworkHandler.CHANNEL.sendToServer(new SpeedBootsNBTPacketC2S(slotContext.index(), speed + actually));
                }
                if (player.level().getGameTime() % 4 == 0) player.playSound(ModSoundEvents.SHOES_WALK.get());
            } else if (speed != 0) {
                NetworkHandler.CHANNEL.sendToServer(new SpeedBootsNBTPacketC2S(slotContext.index(), 0));
            }
            if (player.zza > 0 &&slotContext.entity().level().isClientSide) spawnParticles(slotContext.entity().level(), slotContext.entity().position());
        }
    }

    protected static AttributeModifier getSpeedModifier(ItemStack stack) {
        return new AttributeModifier(SPEED_UUID, "Speed Boots", stack.getOrCreateTag().getInt("speed") * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ImmutableMultimap.of(Attributes.MOVEMENT_SPEED, getSpeedModifier(stack));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), BaseSpeedBoots.class);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }
}
