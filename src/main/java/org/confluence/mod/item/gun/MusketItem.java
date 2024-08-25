package org.confluence.mod.item.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.client.renderer.item.gun.DefaultGunItemRenderer;
import org.confluence.mod.misc.ModArmPoses;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.function.Consumer;

public class MusketItem extends AbstractGunItem {
    public MusketItem() {
        super(ModRarity.GREEN);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private DefaultGunItemRenderer<MusketItem> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    this.renderer = new DefaultGunItemRenderer<>("musket");
                }
                return renderer;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return ModArmPoses.HANDGUN;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int r = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate(r * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);

                poseStack.translate(r * -0.2785682F, 0.05F, 0.15731531F);
                poseStack.mulPose(Axis.XP.rotationDegrees(13.935F));
                poseStack.mulPose(Axis.YP.rotationDegrees(r * 50.3F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(r * -9.785F));
                poseStack.mulPose(Axis.YN.rotationDegrees(r * 45.0F));
                return true;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
}
