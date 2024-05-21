package org.confluence.mod.command;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModArgumentTypeInfos {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> INFOS = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Confluence.MODID);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> GAME_PHASE = INFOS.register("game_phase", () -> ArgumentTypeInfos.
        registerByClass(GamePhaseArgumentType.class, SingletonArgumentInfo.contextFree(GamePhaseArgumentType::gamePhase)));
}
