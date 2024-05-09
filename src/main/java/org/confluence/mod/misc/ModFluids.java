package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;

import java.util.function.Consumer;

public final class ModFluids {
    public static final RegistryObject<FluidType> HONEY_TYPE = RegistryObject.createOptional(new ResourceLocation(Confluence.MODID, "milk"), ForgeRegistries.Keys.FLUID_TYPES.location(), Confluence.MODID);
    public static final RegistryObject<Fluid> HONEY = RegistryObject.create(new ResourceLocation(Confluence.MODID, "milk"), ForgeRegistries.FLUIDS);
    public static final RegistryObject<Fluid> FLOWING_HONEY = RegistryObject.create(new ResourceLocation(Confluence.MODID, "flowing_honey"), ForgeRegistries.FLUIDS);

    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.FLUID_TYPES, helper -> helper.register(HONEY_TYPE.getId(), new FluidType(
            FluidType.Properties.create().density(1024).viscosity(1024)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
        ) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    private static final ResourceLocation HONEY_STILL = new ResourceLocation(Confluence.MODID, "block/honey_still");
                    private static final ResourceLocation HONEY_FLOW = new ResourceLocation(Confluence.MODID, "block/honey_flowing");

                    @Override
                    public ResourceLocation getStillTexture() {
                        return HONEY_STILL;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return HONEY_FLOW;
                    }
                });
            }
        }));

        event.register(ForgeRegistries.Keys.FLUIDS, helper -> {
            ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(HONEY_TYPE, HONEY, FLOWING_HONEY).bucket(ModItems.HONEY_BUCKET);

            helper.register(HONEY.getId(), new ForgeFlowingFluid.Source(properties));
            helper.register(FLOWING_HONEY.getId(), new ForgeFlowingFluid.Flowing(properties));
        });
    }
}
