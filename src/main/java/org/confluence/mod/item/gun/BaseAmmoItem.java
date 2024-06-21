package org.confluence.mod.item.gun;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;

import java.util.Optional;

public class BaseAmmoItem extends Item {
    private final BaseAmmoEntity.Variant variant;

    public BaseAmmoItem(Rarity rarity, BaseAmmoEntity.Variant variant) {
        super(new Properties().rarity(rarity).fireResistant());
        this.variant = variant;
    }

    public BaseAmmoItem(BaseAmmoEntity.Variant variant, Properties properties) {
        super(properties);
        this.variant = variant;
    }

    public BaseAmmoEntity getAmmoEntity(ItemStack itemStack, Player player, Level level) {
        Optional<ItemPrefix> prefix = PrefixProvider.getPrefix(itemStack);
        return new BaseAmmoEntity(player, level, prefix.orElse(null), variant);
    }
}
