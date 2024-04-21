package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.WingsGlidePacketC2S;
import org.confluence.mod.util.CuriosUtils;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;
import top.theillusivec4.curios.api.SlotContext;

public class BaseWings extends BaseCurioItem implements IMayFly, GeoItem, IFallResistance {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final int flyTicks;
    private final double flySpeed;

    public BaseWings(Rarity rarity, int flyTicks, double multiY) {
        super(rarity);
        this.flyTicks = flyTicks;
        this.flySpeed = 0.2 * multiY;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public int getFlyTicks() {
        return flyTicks;
    }

    @Override
    public double getFlySpeed() {
        return flySpeed;
    }

    @Override
    public boolean couldGlide() {
        return true;
    }

    @Override
    public int getFallResistance() {
        return -1;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isLocalPlayer()) {
                boolean onFly = PlayerJumpHandler.isOnGlide();
                if (!player.onGround() && (stack.getTag() == null || stack.getTag().getBoolean("onFly") != onFly)) {
                    NetworkHandler.CHANNEL.sendToServer(new WingsGlidePacketC2S(onFly, slotContext.index()));
                }
            } else {
                long id = GeoItem.getOrAssignId(stack, (ServerLevel) player.level());
                if (player.onGround()) {
                    triggerAnim(player, id, "wings", "standby");
                } else if (stack.getOrCreateTag().getBoolean("onFly")) {
                    triggerAnim(player, id, "wings", "fly");
                } else {
                    triggerAnim(player, id, "wings", "glide");
                }
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), BaseWings.class);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "wings", state -> PlayState.STOP)
            .triggerableAnim("standby", RawAnimation.begin().thenLoop("standby"))
            .triggerableAnim("fly", RawAnimation.begin().thenLoop("fly"))
            .triggerableAnim("glide", RawAnimation.begin().thenLoop("glide"))
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return RenderUtils.getCurrentTick();
    }
}
