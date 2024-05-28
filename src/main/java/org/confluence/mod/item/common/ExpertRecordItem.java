package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class ExpertRecordItem extends RecordItem implements ModRarity.Expert, CustomName {
    public ExpertRecordItem(int comparatorValue, java.util.function.Supplier<SoundEvent> soundSupplier, int lengthInTicks) {
        super(comparatorValue, soundSupplier, new Item.Properties().stacksTo(1).fireResistant().rarity(ModRarity.EXPERT), lengthInTicks);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return withColor(getDescriptionId(pStack));
    }

    @Override
    public String getGenName() {
        return "Music Disc";
    }
}
