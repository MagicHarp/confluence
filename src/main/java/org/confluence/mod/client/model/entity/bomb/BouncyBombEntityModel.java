package org.confluence.mod.client.model.entity.bomb;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.projectile.bombs.BouncyBombEntity;

public class BouncyBombEntityModel extends EntityModel<BouncyBombEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Confluence.asResource("bouncy_bomb_entity"), "main");
    private final ModelPart root;

    public BouncyBombEntityModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 12).addBox(-1.5F, 6.0F, -2.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(2, 0).addBox(-0.5F, 7.0F, -1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(0.0F, 7.0F, -1.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(BouncyBombEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
