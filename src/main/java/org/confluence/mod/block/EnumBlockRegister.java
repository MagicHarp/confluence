package org.confluence.mod.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.EnumRegister;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 对于类似矿石等方块建议使用此interface
 * 在一定程度上可以将ModBlockTagsProvider中的tag耦合进constructor
 */
public interface EnumBlockRegister<E> extends EnumRegister<E> {
    // 各类预设
    EnumBlockRegister.BlockTagsHolder COMPONENT_DIAMOND_TOOL = new BlockTagsHolder(BlockTags.NEEDS_DIAMOND_TOOL);
    EnumBlockRegister.BlockTagsHolder COMPONENT_TIER_4_TOOL = new BlockTagsHolder(ModTags.Blocks.NEEDS_4_LEVEL);
    EnumBlockRegister.BlockTagsHolder COMPONENT_TIER_5_TOOL = new BlockTagsHolder(ModTags.Blocks.NEEDS_5_LEVEL);
    EnumBlockRegister.BlockTagsHolder COMPONENT_TIER_6_TOOL = new BlockTagsHolder(ModTags.Blocks.NEEDS_6_LEVEL);
    EnumBlockRegister.BlockTagsHolder COMPONENT_TIER_7_TOOL = new BlockTagsHolder(ModTags.Blocks.NEEDS_7_LEVEL);
    EnumBlockRegister.BlockTagsHolder COMPONENT_TIER_8_TOOL = new BlockTagsHolder(ModTags.Blocks.NEEDS_8_LEVEL);
    // 矿物预设
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_BASIC = new BlockTagsHolder(BlockTags.MINEABLE_WITH_PICKAXE);
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_DIAMOND_TOOL = new BlockTagsHolder(TAGS_ORE_BASIC, COMPONENT_DIAMOND_TOOL);
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_TIER_4_TOOL = new BlockTagsHolder(TAGS_ORE_BASIC, COMPONENT_TIER_4_TOOL);
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_TIER_5_TOOL = new BlockTagsHolder(TAGS_ORE_BASIC, COMPONENT_TIER_5_TOOL);
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_TIER_6_TOOL = new BlockTagsHolder(TAGS_ORE_BASIC, COMPONENT_TIER_6_TOOL);
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_TIER_7_TOOL = new BlockTagsHolder(TAGS_ORE_BASIC, COMPONENT_TIER_7_TOOL);
    EnumBlockRegister.BlockTagsHolder TAGS_ORE_TIER_8_TOOL = new BlockTagsHolder(TAGS_ORE_BASIC, COMPONENT_TIER_8_TOOL);

    BlockTagsHolder getBlockTags();
    /**
     * 此类的实例可存储方块的tags，并在ModBlockTagsProvider中自动化添加
     */
    class BlockTagsHolder {
        Set<TagKey<Block>> tags;
        public Set<TagKey<Block>> getTags() {
            return this.tags;
        }

        public BlockTagsHolder() {
            this.tags = new HashSet<>();
        }
        @SafeVarargs
        public BlockTagsHolder(TagKey<Block>... tags) {
            this();
            this.tags.addAll(List.of(tags));
        }
        public BlockTagsHolder(BlockTagsHolder... holders) {
            this();
            for (BlockTagsHolder holder : holders) {
                this.tags.addAll(holder.getTags());
            }
        }
    }
}
