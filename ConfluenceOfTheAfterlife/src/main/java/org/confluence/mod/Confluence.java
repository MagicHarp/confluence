package org.confluence.mod;

import com.mojang.datafixers.util.Function3;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import org.confluence.mod.common.init.*;
import org.confluence.mod.common.init.block.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Hashtable;

@Mod(Confluence.MODID)
public class Confluence {
    public static final String MODID = "confluence";
    public static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(MODID);
    public static final Hashtable<EntityType<? extends AbstractMinecart>, Item> MINECART_CURIO = new Hashtable<>(); // 矿车类型 -> 矿车物品
    public static final Hashtable<Item, Function3<Level, BlockPos, Double, AbstractMinecart>> CURIO_MINECART = new Hashtable<>(); // 矿车物品 -> 矿车实体
    public static NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID).skipErrors(true)); // todo 销毁
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");

    public Confluence(IEventBus eventBus, ModContainer container) {
        ModTabs.TABS.register(eventBus);
        ModBlocks.register(eventBus);
        ModItems.register(eventBus);
        ModEntities.ENTITIES.register(eventBus);
        ModDataComponentTypes.DATA_COMPONENT_TYPE.register(eventBus);
        ModSoundEvents.SOUND_EVENT.register(eventBus);
        ModAttachments.TYPES.register(eventBus);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static void registerMinecartAbility() {
        MINECART_CURIO.put(EntityType.MINECART, Items.MINECART);
        CURIO_MINECART.put(Items.MINECART, (level, blockPos, offsetY) -> new Minecart(level, blockPos.getX() + 0.5, blockPos.getY() + 0.0625 + offsetY, blockPos.getZ() + 0.5));
    }
}
