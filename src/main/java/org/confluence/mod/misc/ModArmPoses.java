package org.confluence.mod.misc;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;

public final class ModArmPoses {
    public static HumanoidModel.ArmPose HANDGUN;

    public static void initialize() {
        HANDGUN = HumanoidModel.ArmPose.create("confluence$handgun", true, (model, entity, arm) -> {
            model.rightArm.yRot = -0.1F + model.head.yRot;
            model.leftArm.yRot = 0.1F + model.head.yRot + 0.4F;
            model.rightArm.xRot = -Mth.HALF_PI + model.head.xRot;
            model.leftArm.xRot = -Mth.HALF_PI + model.head.xRot;
        });
    }
}
