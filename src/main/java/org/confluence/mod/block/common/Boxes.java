package org.confluence.mod.block.common;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.util.EnumRegister;
import org.jetbrains.annotations.NotNull;

public enum Boxes implements EnumRegister<Boxes.BaseBoxBlock> {
    WOODEN_BOX("wooden_box"),
    IRON_BOX("iron_box"),
    GOLDEN_BOX("golden_box"),
    JUNGLE_BOX("jungle_box"),
    SKY_BOX("sky_box"),
    CORRUPT_BOX("corrupt_box"),
    TR_CRIMSON_BOX("tr_crimson_box"),
    SACRED_BOX("sacred_box"),
    DUNGEON_BOX("dungeon_box"),
    FREEZE_BOX("freeze_box"),
    OASIS_BOX("oasis_box"),
    OBSIDIAN_BOX("obsidian_box"),
    OCEAN_BOX("ocean_box"),
    // 肉后
    PEARLWOOD_BOX("pearlwood_box"),
    MITHRIL_BOX("mithril_box"),
    TITANIUM_BOX("titanium_box"),
    THORNS_BOX("thorns_box"),
    SPACE_BOX("space_box"),
    DEFACED_BOX("defaced_box"),
    BLOOD_BOX("blood_box"),
    PROVIDENTIAL_BOX("providential_box"),
    FENCING_BOX("fencing_box"),
    CONIFEROUS_WOOD_BOX("coniferous_wood_box"),
    ILLUSION_BOX("illusion_box"),
    HELL_STONE_BOX("hell_stone_box"),
    BEACH_BOX("beach_box");

    private final RegistryObject<BaseBoxBlock> value;

    Boxes(String id) {
        this.value = ModBlocks.registerWithItem(id, BaseBoxBlock::new, block -> () ->
            new Item(block.get(), Confluence.asResource("gameplay/box/" + id)));
    }

    @Override
    public RegistryObject<BaseBoxBlock> getValue() {
        return value;
    }

    public static void init() {}

    public static class BaseBoxBlock extends Block {
        public BaseBoxBlock() {
            super(Properties.copy(Blocks.OAK_PLANKS));
        }
    }

    public static class Item extends BlockItem {
        private final ResourceLocation lootTable;

        public Item(Block pBlock, ResourceLocation lootTable) {
            super(pBlock, new Properties());
            this.lootTable = lootTable;
        }

        @Override
        public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (level instanceof ServerLevel serverLevel && hand == InteractionHand.MAIN_HAND && !player.isCrouching()) {
                AtomicDouble fishingPower = new AtomicDouble();
                player.getCapability(AbilityProvider.CAPABILITY)
                    .ifPresent(playerAbility -> fishingPower.set(playerAbility.getFishingPower(player)));
                LootParams lootparams = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withLuck(fishingPower.floatValue())
                    .create(LootContextParamSets.GIFT);
                LootTable loottable = serverLevel.getServer().getLootData().getLootTable(lootTable);
                for (ItemStack loot : loottable.getRandomItems(lootparams)) {
                    if (!player.addItem(loot)) player.drop(loot, false, false);
                }
                itemStack.shrink(1);
                serverLevel.playSound(null, player.blockPosition(), ModSoundEvents.TERRA_OPERATION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            return InteractionResultHolder.success(itemStack);
        }

        @Override
        protected boolean canPlace(BlockPlaceContext pContext, @NotNull BlockState pState) {
            return pContext.getPlayer() == null || pContext.getPlayer().isCrouching();
        }
    }
}
