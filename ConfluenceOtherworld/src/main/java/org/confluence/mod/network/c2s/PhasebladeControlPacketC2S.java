package org.confluence.mod.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.confluence.lib.common.LibAttributes;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.entity.projectile.sword.PhasebladeProjectile;
import org.confluence.mod.common.init.entity.ModEntities;
import org.confluence.mod.common.item.sword.BasePhasebladeItem;
import org.confluence.mod.common.item.sword.Phasesaber;
import org.mesdag.portlib.network.IPortPacket;
import org.mesdag.portlib.network.codec.PortStreamCodec;

import java.util.List;

public record PhasebladeControlPacketC2S(Action action) implements IPortPacket.C2S {
    public static final ResourceLocation ID = Confluence.asResource("phaseblade_control");
    public static final PortStreamCodec<ByteBuf, PhasebladeControlPacketC2S> STREAM_CODEC = new PortStreamCodec<>() {
        @Override
        public PhasebladeControlPacketC2S decode(ByteBuf buffer) {return new PhasebladeControlPacketC2S(buffer.readBoolean() ? Action.THROW : Action.RECALL);}

        @Override
        public void encode(ByteBuf buffer, PhasebladeControlPacketC2S packet) {buffer.writeBoolean(packet.action == Action.THROW);}
    };

    @Override
    public ResourceLocation identifier() {return ID;}

    @Override
    public void work(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof BasePhasebladeItem) || !BasePhasebladeItem.isTurnOn(stack))
            return;
        List<PhasebladeProjectile> active = player.serverLevel().getEntitiesOfClass(PhasebladeProjectile.class,
                AABB.ofSize(player.position(), 256.0D, 256.0D, 256.0D), projectile -> projectile.belongsTo(player));
        if (action == Action.RECALL) {
            active.forEach(PhasebladeProjectile::recall);
            return;
        }
        if (!active.isEmpty()) {
            boolean replacingStuckBlade = active.stream().allMatch(projectile -> projectile.state() == PhasebladeProjectile.State.STUCK)
                    && active.stream().anyMatch(projectile -> projectile.getItem().getItem() != stack.getItem());
            if (!replacingStuckBlade) return;
            active.forEach(PhasebladeProjectile::recall);
        }
        PhasebladeProjectile projectile = stack.getItem() instanceof Phasesaber
                ? ModEntities.PHASESABER_PROJECTILE.get().create(player.serverLevel())
                : ModEntities.PHASEBLADE_PROJECTILE.get().create(player.serverLevel());
        if (projectile == null) return;
        projectile.configure(player, stack,
                (float) player.getAttributeValue(LibAttributes.getAttackDamage()),
                (float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK));
        player.serverLevel().addFreshEntity(projectile);
    }

    public static void sendThrow() {Confluence.NETWORK_HANDLER.sendToServer(new PhasebladeControlPacketC2S(Action.THROW));}

    public static void sendRecall() {Confluence.NETWORK_HANDLER.sendToServer(new PhasebladeControlPacketC2S(Action.RECALL));}

    public enum Action {
        THROW,
        RECALL
    }
}
