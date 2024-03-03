package org.confluence.mod.block.cloaked;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

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

    public static void createRevelation(String step, BlockState clockedBlock, String cloakBlock, String nameReplacement) {
        String source = clockedBlock.toString();
        JsonObject jsonObject = new Gson().fromJson("""
            {
                "advancement": "%s",
                "block_states": {
                    "%s": "%s"
                },
                "block_name_replacements": {
                    "%s": "%s"
                }
            }
            """.formatted(step, source, cloakBlock, source, nameReplacement), JsonObject.class);
        RevelationRegistry.registerFromJson(jsonObject);
    }
}
