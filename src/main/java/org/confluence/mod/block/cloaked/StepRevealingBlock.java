package org.confluence.mod.block.cloaked;

import com.google.gson.JsonObject;
import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.confluence.mod.Confluence;

import java.util.Map;

public class StepRevealingBlock extends Block {
    public static final IntegerProperty REVEAL_STEP = IntegerProperty.create("reveal_step", 0, 2);

    public StepRevealingBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(REVEAL_STEP, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(REVEAL_STEP);
    }

    public static void create(Block blockA, Block blockB, int state, int step) {
        String a = BlockStateParser.serialize(blockA.defaultBlockState().setValue(REVEAL_STEP, state));
        String b = BlockStateParser.serialize(blockB.defaultBlockState().setValue(REVEAL_STEP, state));
        JsonObject revJson = Confluence.GSON.toJsonTree(Map.of(
            "advancement", "confluence:reveal/step" + step,
            "block_states", Map.of(
                a, "minecraft:deepslate",
                b, "minecraft:deepslate"
            ),
            "block_name_replacements", Map.of(
                a, "block.minecraft.deepslate",
                b, "block.minecraft.deepslate"
            )
        ), JsonObject.class).getAsJsonObject();
        RevelationRegistry.registerFromJson(revJson);
    }
}
