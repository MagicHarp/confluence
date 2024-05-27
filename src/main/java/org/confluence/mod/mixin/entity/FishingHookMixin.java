package org.confluence.mod.mixin.entity;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.item.fishing.Baits;
import org.confluence.mod.misc.ModLootTables;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.IFishingHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin implements IFishingHook {
    @Shadow
    @Nullable
    public abstract Player getPlayerOwner();

    @Shadow
    @Final
    private int luck;
    @Unique
    private boolean c$isLavaHook = false;
    @Unique
    private ItemStack c$bait = null;

    @Unique
    @Override
    public void c$setIsLavaHook() {
        this.c$isLavaHook = true;
    }

    @Unique
    @Override
    public ItemStack c$getBait() {
        return c$bait;
    }

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private TagKey<Fluid> isLavaTag(TagKey<Fluid> pTag) {
        if (c$isLavaHook) return ModTags.FISHING_ABLE;
        return pTag;
    }

    @Redirect(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean isLavaBlock(BlockState instance, Block block) {
        if (c$isLavaHook) return instance.is(block) || instance.is(Blocks.LAVA);
        return instance.is(block);
    }

    @ModifyArg(method = "getOpenWaterTypeForBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private TagKey<Fluid> fluidType(TagKey<Fluid> pTag) {
        if (c$isLavaHook) return ModTags.FISHING_ABLE;
        return pTag;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 0), index = 0)
    private ParticleOptions smokeParticle(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.SMOKE;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 1), index = 0)
    private ParticleOptions flameParticle(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.FLAME;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 2), index = 0)
    private ParticleOptions flameParticle2(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.FLAME;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 3), index = 0)
    private ParticleOptions smokeParticle2(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.SMOKE;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 4), index = 0)
    private ParticleOptions flameParticle3(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.FLAME;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 5), index = 0)
    private ParticleOptions lavaParticle(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.LAVA;
        return pType;
    }

    @ModifyArg(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootDataManager;getLootTable(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private ResourceLocation loot(ResourceLocation par1) {
        if (c$isInLava()) return ModLootTables.FISHING_LAVA;
        return par1;
    }

    @Redirect(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootParams$Builder;create(Lnet/minecraft/world/level/storage/loot/parameters/LootContextParamSet;)Lnet/minecraft/world/level/storage/loot/LootParams;"))
    private LootParams getLuck(LootParams.Builder builder, LootContextParamSet set) {
        Player owner = getPlayerOwner();
        AtomicDouble fishing = new AtomicDouble(luck);
        if (owner != null) {
            Optional<PlayerAbility> optional = owner.getCapability(AbilityProvider.CAPABILITY).resolve();
            if (optional.isPresent()) {
                fishing.addAndGet(optional.get().getFishingPower(owner));
            } else {
                fishing.addAndGet(owner.getLuck());
            }
            Inventory inventory = owner.getInventory();
            float bonus = 1.0F;
            for (ItemStack itemStack : inventory.offhand) {
                if (itemStack.getItem() instanceof Baits.IBait iBait) {
                    this.c$bait = itemStack;
                    bonus += iBait.getBaitBonus();
                    break;
                }
                this.c$bait = null;
            }
            if (c$bait == null) {
                for (ItemStack itemStack : inventory.items) {
                    if (itemStack.getItem() instanceof Baits.IBait iBait) {
                        this.c$bait = itemStack;
                        bonus += iBait.getBaitBonus();
                        break;
                    }
                }
            }
            if (c$bait != null) fishing.set(fishing.get() * bonus);
        }
        return builder.withLuck(fishing.floatValue()).create(set);
    }

    @Unique
    private FishingHook c$getSelf() {
        return (FishingHook) (Object) this;
    }

    @Unique
    private boolean c$isInLava() {
        FishingHook self = c$getSelf();
        return self.level().getFluidState(self.blockPosition()).is(FluidTags.LAVA);
    }
}
