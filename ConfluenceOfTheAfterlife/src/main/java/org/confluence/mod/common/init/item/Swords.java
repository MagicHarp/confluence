package org.confluence.mod.common.init.item;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.init.ModItems;
import org.confluence.mod.common.init.ModTiers;
import org.confluence.mod.common.item.sword.BoardSwordItem;
import org.confluence.mod.common.item.sword.ShortSwordItem;

import java.util.function.Supplier;

public class Swords {
    public static final DeferredRegister.Items SWORDS = DeferredRegister.createItems(Confluence.MODID);

    public static final DeferredItem<SwordItem> COPPER_SHORT_SWORD = registerShortSword("copper_short_sword", ModTiers.COPPER, 2, 3);
    public static final DeferredItem<SwordItem> TIN_SHORT_SWORD = registerShortSword("tin_short_sword", ModTiers.TIN, 3, 3);
    public static final DeferredItem<SwordItem> LEAD_SHORT_SWORD = registerShortSword("lead_short_sword", ModTiers.LEAD, 3, 3);
    public static final DeferredItem<SwordItem> SILVER_SHORT_SWORD = registerShortSword("silver_short_sword", ModTiers.SILVER, 4, 3);
    public static final DeferredItem<SwordItem> TUNGSTEN_SHORT_SWORD = registerShortSword("tungsten_short_sword", ModTiers.TUNGSTEN, 4, 3);
    public static final DeferredItem<SwordItem> GOLDEN_SHORT_SWORD = registerShortSword("golden_short_sword", ModTiers.GOLD, 5, 3);
    public static final DeferredItem<SwordItem> PLATINUM_SHORT_SWORD = registerShortSword("platinum_short_sword", Tiers.DIAMOND, 5, 3);

    public static final DeferredItem<SwordItem> CACTUS_SWORD = registerBoardSword("cactus_sword", ModTiers.CACTUS, 4, 1.6F);
    public static final DeferredItem<SwordItem> COPPER_BOARD_SWORD = registerBoardSword("copper_board_sword", ModTiers.COPPER, 4, 1.6F);
    public static final DeferredItem<SwordItem> TIN_BOARD_SWORD = registerBoardSword("tin_board_sword", ModTiers.TIN, 4, 1.6F);
    public static final DeferredItem<SwordItem> LEAD_BOARD_SWORD = registerBoardSword("lead_board_sword", ModTiers.LEAD, 5, 1.6F);
    public static final DeferredItem<SwordItem> SILVER_BOARD_SWORD = registerBoardSword("silver_board_sword", ModTiers.SILVER, 5, 1.6F);
    public static final DeferredItem<SwordItem> TUNGSTEN_BOARD_SWORD = registerBoardSword("tungsten_board_sword", ModTiers.TUNGSTEN, 6, 1.6F);
    public static final DeferredItem<SwordItem> GOLDEN_BOARD_SWORD = registerBoardSword("golden_board_sword", ModTiers.GOLD, 7, 1.6F);
    public static final DeferredItem<SwordItem> PLATINUM_BOARD_SWORD = registerBoardSword("platinum_board_sword", Tiers.DIAMOND, 7, 1.6F);












    public static DeferredItem<SwordItem> register(String name, Supplier<SwordItem> supplier) {
        return SWORDS.register(name,supplier);
    }

    public static DeferredItem<SwordItem> registerShortSword(String name, Tier tier, int rawDamage, float rawSpeed) {
        return SWORDS.register(name,()-> new ShortSwordItem(tier, ModRarity.WHITE,rawDamage, rawSpeed));
    }
    public static DeferredItem<SwordItem> registerShortSword(String name, Tier tier, int rawDamage, float rawSpeed ,ModRarity rarity) {
        return SWORDS.register(name,()-> new ShortSwordItem(tier, rarity,rawDamage, rawSpeed));
    }
    public static DeferredItem<SwordItem> registerBoardSword(String name, Tier tier, int rawDamage, float rawSpeed) {
        return SWORDS.register(name,()-> new BoardSwordItem(tier, ModRarity.WHITE,rawDamage, rawSpeed));
    }
    public static DeferredItem<SwordItem> registerBoardSword(String name, Tier tier, int rawDamage, float rawSpeed ,ModRarity rarity) {
        return SWORDS.register(name,()-> new BoardSwordItem(tier, rarity,rawDamage, rawSpeed));
    }



}
