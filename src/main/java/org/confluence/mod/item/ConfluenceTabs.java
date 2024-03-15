package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.DecorativeBlocks;
import org.confluence.mod.block.Ores;
import org.confluence.mod.item.common.ExoticPotions;
import org.confluence.mod.item.magic.Weapons;

import static org.confluence.mod.block.ConfluenceBlocks.*;

public class ConfluenceTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);
    //建筑方块
    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = TABS.register("building_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.BLOCKS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.building_blocks"))
            .displayItems((parameters, output) -> {
                for (DecorativeBlocks decorativeBlocks : DecorativeBlocks.values()) {
                    output.accept(decorativeBlocks.get());
                }
                output.accept(BIG_RUBY_BLOCK.get());
                output.accept(BIG_AMBER_BLOCK.get());
                output.accept(BIG_TOPAZ_BLOCK.get());
                output.accept(BIG_ANOTHER_EMERALD_BLOCK.get());
                output.accept(BIG_SAPPHIRE_BLOCK.get());
                output.accept(BIG_ANOTHER_AMETHYST_BLOCK.get());
                output.accept(ANOTHER_POLISHED_GRANITE.get());
                output.accept(POLISHED_MARBLE.get());
            })
            .build());
    //自然方块
    public static final RegistryObject<CreativeModeTab> NATURAL_BLOCKS = TABS.register("natural_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.NATURE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.natural_blocks"))
            .displayItems((parameters, output) -> {
                for (Ores ores : Ores.values()) {
                    output.accept(ores.get());
                }
            })
            .build());
    //材料
    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.ITEM_ICON.get()))
            .title(Component.translatable("creativetab.confluence.materials"))
            .displayItems((parameters, output) -> {
                for (Materials materials : Materials.values()) {
                    output.accept(materials.get());
                }
                for (SlimeBalls slimeBalls : SlimeBalls.values()) {
                    output.accept(slimeBalls.get());
                }
            })
            .build());
    //创造者物品栏
    public static final RegistryObject<CreativeModeTab> CREATOR_ITEMS = TABS.register("creator_items",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.CREATIVE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.creator_items"))
            .displayItems((parameters, output) -> {

            })
            .build());
    //生物
    public static final RegistryObject<CreativeModeTab> CREATURE = TABS.register("creature",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.ENEMY_ICON.get()))
            .title(Component.translatable("creativetab.confluence.creature"))
            .displayItems((parameters, output) -> {

            })
            .build());
    //工具
    public static final RegistryObject<CreativeModeTab> TOOL = TABS.register("tool",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.TOOLS_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.tool"))
                    .displayItems((parameters, output) -> {
                        for (Pickaxes pickaxes :Pickaxes.values()){
                            output.accept(pickaxes.get());
                        }
                        for (HammerAxes hammerAxes :HammerAxes.values()){
                            output.accept(hammerAxes.get());
                        }
                        for (Axes axes :Axes.values()){
                            output.accept(axes.get());

                        }
                        for (Hammers hammers :Hammers.values()){
                            output.accept(hammers.get());

                        }

                    })
                    .build());

    //战士武器
    public static final RegistryObject<CreativeModeTab> WARRIOR = TABS.register("warrior",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.MELEE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.warrior"))
            .displayItems((parameters, output) -> {
                for (Swords swords :Swords.values()){
                    output.accept(swords.get());



                }

            })
            .build());
    //射手武器
    public static final RegistryObject<CreativeModeTab> SHOOTER = TABS.register("shooter",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.REMOTE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.shooter"))
            .displayItems((parameters, output) -> {

            })
            .build());
    //法师武器
    public static final RegistryObject<CreativeModeTab> MAGE = TABS.register("mage",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.MAGIC_ICON.get()))
            .title(Component.translatable("creativetab.confluence.mage"))
            .displayItems((parameters, output) -> {
                 for (Weapons weapons : Weapons.values()) {
                     output.accept(weapons.get());
                 }
            })
            .build());
    //召唤武器
    public static final RegistryObject<CreativeModeTab> SUMMONER = TABS.register("summoner",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.SUMMON_ICON.get()))
            .title(Component.translatable("creativetab.confluence.summoner"))
            .displayItems((parameters, output) -> {

            })
            .build());
    //食物与药水
    public static final RegistryObject<CreativeModeTab> FOOD_AND_POTIONS = TABS.register("food_and_potions",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Icons.SUMMON_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.food_and_potions"))
                    .displayItems((parameters, output) -> {
                        for (ExoticPotions exoticPotions : ExoticPotions.values()) {
                            output.accept(exoticPotions.get());
                        }
                    })
                    .build());
}
