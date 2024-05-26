package org.confluence.mod.entity.hook;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class MimicHookEntity extends AbstractHookEntity implements VariantHolder<MimicHookEntity.Variant> {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(MimicHookEntity.class, EntityDataSerializers.INT);

    public MimicHookEntity(EntityType<MimicHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public MimicHookEntity(AbstractHookItem item, Player player, Level level, Variant variant) {
        super(ModEntities.MIMIC_HOOK.get(), item, player, level);
        setVariant(variant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_VARIANT_ID, 0);
    }

    @Override
    public void setVariant(Variant pVariant) {
        entityData.set(DATA_VARIANT_ID, pVariant.id);
    }

    @Override
    public @NotNull Variant getVariant() {
        return Variant.byId(entityData.get(DATA_VARIANT_ID));
    }

    public enum Variant implements StringRepresentable {
        ILLUMINANT(0, "illuminant"),
        WORM(1, "worm"),
        TENDON(2, "tendon");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public static Variant byId(int pId) {
            return BY_ID.apply(pId);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
