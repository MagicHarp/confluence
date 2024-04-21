package org.confluence.mod.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.confluence.mod.client.model.curio.GeckoCurioModel;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.movement.BaseWings;

public class FledglingWingsRenderer extends GeckoCurioRenderer<BaseWings> {
    public FledglingWingsRenderer() {
        super(new GeckoCurioModel<>("fledgling_wings"), CurioItems.FLEDGLING_WINGS.get(BaseWings.class));
    }

    @Override
    public void modifyPoseStack(PoseStack poseStack) {
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.translate(-0.5F, -2.0F, -0.5F);
    }
}
