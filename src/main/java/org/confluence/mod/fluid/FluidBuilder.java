package org.confluence.mod.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Hashtable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FluidBuilder {
    static final Hashtable<ResourceLocation, FluidBuilder> BUILDERS = new Hashtable<>();
    public final RegistryObject<FluidType> fluidType;
    public final RegistryObject<FlowingFluid> fluid;
    public final RegistryObject<FlowingFluid> flowingFluid;
    private final String namespace;
    private FluidType.Properties properties;

    private IClientFluidTypeExtensions extensions;
    private ResourceLocation stillTexture;
    private ResourceLocation flowingTexture;

    private Supplier<? extends LiquidBlock> block;
    private Supplier<? extends Item> bucket;
    private Function<ForgeFlowingFluid.Properties, FlowingFluid> source;
    private Function<ForgeFlowingFluid.Properties, FlowingFluid> flowing;
    private Function<FluidType.Properties, FluidType> fluidTypeFunction;

    FluidBuilder(ResourceLocation location) {
        this.namespace = location.getNamespace();
        String id = location.getPath();
        this.fluidType = RegistryObject.createOptional(location, ForgeRegistries.Keys.FLUID_TYPES.location(), namespace);
        this.fluid = RegistryObject.create(location, ForgeRegistries.FLUIDS);
        this.flowingFluid = RegistryObject.create(new ResourceLocation(namespace, "flowing_" + id), ForgeRegistries.FLUIDS);
    }

    public FluidBuilder properties(FluidType.Properties properties) {
        this.properties = properties;
        return this;
    }

    public FluidBuilder customClient(IClientFluidTypeExtensions extensions) {
        this.extensions = extensions;
        return this;
    }

    public FluidBuilder stillTexture(String texture) {
        this.stillTexture = new ResourceLocation(namespace, texture);
        return this;
    }

    public FluidBuilder flowingTexture(String texture) {
        this.flowingTexture = new ResourceLocation(namespace, texture);
        return this;
    }

    public FluidBuilder block(Supplier<? extends LiquidBlock> block) {
        this.block = block;
        return this;
    }

    public FluidBuilder bucket(Supplier<? extends Item> bucket) {
        this.bucket = bucket;
        return this;
    }

    public FluidBuilder source(Function<ForgeFlowingFluid.Properties, FlowingFluid> function) {
        this.source = function;
        return this;
    }

    public FluidBuilder flowing(Function<ForgeFlowingFluid.Properties, FlowingFluid> function) {
        this.flowing = function;
        return this;
    }

    public FluidBuilder customType(Function<FluidType.Properties, FluidType> function) {
        this.fluidTypeFunction = function;
        return this;
    }

    public FluidTriple build() {
        return new FluidTriple(fluidType, fluid, flowingFluid);
    }

    public static void register(RegisterEvent event) {
        BUILDERS.forEach((location, builder) -> {
            event.register(ForgeRegistries.Keys.FLUID_TYPES, helper -> helper.register(location, builder.fluidTypeFunction == null ? new FluidType(builder.properties) {
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(builder.extensions == null ? new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return builder.stillTexture;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return builder.flowingTexture;
                        }
                    } : builder.extensions);
                }
            } : builder.fluidTypeFunction.apply(builder.properties)));

            event.register(ForgeRegistries.Keys.FLUIDS, helper -> {
                ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid
                        .Properties(builder.fluidType, builder.fluid, builder.flowingFluid)
                        .block(builder.block).bucket(builder.bucket);

                helper.register(builder.fluid.getId(), builder.source == null ? new ForgeFlowingFluid.Source(properties) : builder.source.apply(properties));
                helper.register(builder.flowingFluid.getId(), builder.flowing == null ? new ForgeFlowingFluid.Flowing(properties) : builder.flowing.apply(properties));
            });
        });
    }
}
