package org.confluence.mod.item.sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.client.renderer.item.LightSaberRenderer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Consumer;

public class LightSaber extends BoardSwordItem implements GeoItem {
    public static final UUID SABER_MODIFIER_UUID = UUID.fromString("C07E8BEF-215A-4AF8-81AF-C435420D9A3F");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Multimap<Attribute, AttributeModifier> attributeModifiers;
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
        }, 0, 3, new Properties().fireResistant());
        this.color = color;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            if (attributeModifiers == null) {
                attributeModifiers = super.getDefaultAttributeModifiers(slot);
            }
            return attributeModifiers;
        }
        return ImmutableMultimap.of();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean onUse = isOnUse(itemStack);
        level.playSound(player, player.getOnPos().above(), onUse ? SoundEvents.BEACON_DEACTIVATE : SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2, 1);

        if (!level.isClientSide) {
            if (onUse) {
                attributeModifiers.entries().removeIf(entry -> entry.getKey() == Attributes.ATTACK_DAMAGE && entry.getValue().getId() == SABER_MODIFIER_UUID);
            } else {
                attributeModifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(SABER_MODIFIER_UUID, "Saber Modifier", 6, AttributeModifier.Operation.ADDITION));
            }
        }

        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return isOnUse(itemStack) ? UseAnim.NONE : UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return isOnUse(itemStack) ? 5 : 15;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living) {
        if (level.isClientSide) return itemStack;

        triggerAnim(living, GeoItem.getOrAssignId(itemStack, (ServerLevel) level), "light", isOnUse(itemStack) ? "off" : "on");
        if (living instanceof Player player) player.getCooldowns().addCooldown(this, 10);
        return itemStack;
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "light", state -> PlayState.STOP)
            .triggerableAnim("on", RawAnimation.begin().thenPlayAndHold("turn_on"))
            .triggerableAnim("off", RawAnimation.begin().thenPlayAndHold("turn_off"))
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private boolean isOnUse(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("onUse");
    }
}
