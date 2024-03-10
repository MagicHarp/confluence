package org.confluence.mod.block.cloaked;

import com.google.gson.JsonObject;
import de.dafuqs.revelationary.RevelationRegistry;
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

    public static void createRevelation(String step, BlockState clockedBlock) {
        String source = clockedBlock.toString();
        JsonObject revJson = Confluence.GSON.toJsonTree(Map.of(
            "advancement", step,
            "block_states", Map.of(source, "minecraft:deepslate"),
            "block_name_replacements", Map.of(source, "block.minecraft.deepslate")
        ), JsonObject.class).getAsJsonObject();
        RevelationRegistry.registerFromJson(revJson);
    }
}
