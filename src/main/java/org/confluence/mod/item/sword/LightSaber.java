package org.confluence.mod.item.sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.client.renderer.item.LightSaberRenderer;
import org.confluence.mod.item.magic.IMagicAttack;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class LightSaber extends BoardSwordItem implements GeoItem, IMagicAttack {
    private static final ImmutableMultimap<Attribute, AttributeModifier> ON = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 9, AttributeModifier.Operation.ADDITION),
        Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION)
    );
    private static final ImmutableMultimap<Attribute, AttributeModifier> OFF = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 0, AttributeModifier.Operation.ADDITION),
        Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION)
    );

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String color;

    public LightSaber(String color) {
        super(new Tier() {
            @Override
            public int getUses() {
                return -1;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        }, 10, 3, new Properties().fireResistant());
        this.color = color;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack itemStack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return isTurnOff(itemStack) ? OFF : ON;
        }
        return ImmutableMultimap.of();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 20;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living) {
        if (level.isClientSide) return itemStack;
        boolean turnOff = isTurnOff(itemStack);
        itemStack.getOrCreateTag().putBoolean("turnOff", !turnOff);
        triggerAnim(living, GeoItem.getOrAssignId(itemStack, (ServerLevel) level), "light", turnOff ? "on" : "off");
        level.playSound(living, living.getOnPos().above(), isTurnOff(itemStack) ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2, 1);
        if (living instanceof Player player) player.getCooldowns().addCooldown(this, 10);
        return itemStack;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int tick, boolean selected) {
        if (selected && tick % 20 == 0 && !level.isClientSide && !isTurnOff(itemStack) && entity instanceof ServerPlayer serverPlayer) {
            if (PlayerUtils.extractMana(serverPlayer, () -> 1)) return;
            itemStack.getOrCreateTag().putBoolean("turnOff", true);
            triggerAnim(entity, GeoItem.getOrAssignId(itemStack, (ServerLevel) level), "light", "off");
            level.playSound(entity, entity.getOnPos().above(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2, 1);
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private LightSaberRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new LightSaberRenderer(color);
                }
                return renderer;
            }
        });
    }

    public static final RawAnimation onAnim = RawAnimation.begin().thenPlay("turn_on");
    public static final RawAnimation offAnim = RawAnimation.begin().thenPlay("turn_off");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "light", state -> PlayState.STOP)
            .triggerableAnim("on", onAnim)
            .triggerableAnim("off", offAnim)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private boolean isTurnOff(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("turnOff");
    }
}
