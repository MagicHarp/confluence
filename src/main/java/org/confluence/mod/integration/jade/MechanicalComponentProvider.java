package org.confluence.mod.integration.jade;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.block.functional.mechanical.NetworkNode;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Comparator;

public class MechanicalComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static final MechanicalComponentProvider INSTANCE = new MechanicalComponentProvider();
    public static final ResourceLocation UID = new ResourceLocation(Confluence.MODID, "jade_mechanical_component");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag compoundTag = blockAccessor.getServerData();
        if (compoundTag.get("networkInfo") instanceof ListTag listTag) {
            listTag.forEach(tag -> {
                if (tag instanceof CompoundTag tag1) {
                    int color = tag1.getInt("color");
                    String hexString = Integer.toHexString(color).toUpperCase();
                    hexString = "0".repeat(6 - hexString.length()) + hexString;
                    iTooltip.add(Component.translatable("info.confluence.network", hexString, tag1.getBoolean("signal"))
                        .withStyle(style -> style.withColor(color))
                    );
                }
            });
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof AbstractMechanicalBlock.Entity entity) {
            NetworkNode networkNode = entity.getNetworkNode();
            if (networkNode == null) return;
            ListTag listTag = new ListTag();
            networkNode.getNetworks().int2ObjectEntrySet().stream()
                .sorted(Comparator.comparingInt(Int2ObjectMap.Entry::getIntKey))
                .forEach(entry -> {
                    CompoundTag tag = new CompoundTag();
                    tag.putInt("color", entry.getIntKey());
                    tag.putBoolean("signal", entry.getValue().hasSignal());
                    listTag.add(tag);
                });
            compoundTag.put("networkInfo", listTag);
        }
    }
}
