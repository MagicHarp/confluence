package org.confluence.mod.misc;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;

public final class ModArmPoses {
    public static HumanoidModel.ArmPose HANDGUN;

    public static void initialize() {
        HANDGUN = HumanoidModel.ArmPose.create("confluence$handgun", true, (model, entity, arm) -> {
            model.leftArm.xRot = 90.0F;
            model.leftArm.yRot = 45.0F * Mth.DEG_TO_RAD;
        });
    }
}
