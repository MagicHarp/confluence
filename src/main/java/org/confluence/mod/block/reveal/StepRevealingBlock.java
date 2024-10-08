package org.confluence.mod.block.reveal;

import com.google.gson.JsonObject;
import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.confluence.mod.block.natural.Ores;

public class StepRevealingBlock extends Block implements IRevealed {
    public static final IntegerProperty REVEAL_STEP = IntegerProperty.create("reveal_step", 0, 2);

    public StepRevealingBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(REVEAL_STEP, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(REVEAL_STEP);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        BlockState cloak = RevelationRegistry.getCloak(state);
        return super.canHarvestBlock(cloak == null ? state : cloak, level, pos, player);
    }

    public static void create(int state, int step, Block... blocks) {
        JsonObject stateJson = new JsonObject();
        for (Block block : blocks) {
            String str = BlockStateParser.serialize(block.defaultBlockState().setValue(REVEAL_STEP, state));
            stateJson.addProperty(str, "minecraft:deepslate");
        }
        JsonObject revJson = new JsonObject();
        revJson.addProperty("advancement", "confluence:reveal/step" + step);
        revJson.add("block_states", stateJson);
        RevelationRegistry.registerFromJson(revJson);
    }

    public static void registerOurOwn() {
        int step = 0;
        for (int state = 0; state < 3; state++) {
            create(state, step++, Ores.DEEPSLATE_COBALT_ORE.get(), Ores.DEEPSLATE_PALLADIUM_ORE.get());
            create(state, step++, Ores.DEEPSLATE_MITHRIL_ORE.get(), Ores.DEEPSLATE_ORICHALCUM_ORE.get());
            create(state, step++, Ores.DEEPSLATE_ADAMANTITE_ORE.get(), Ores.DEEPSLATE_TITANIUM_ORE.get());
        }
    }
}
