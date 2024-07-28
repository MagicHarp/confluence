package org.confluence.mod.client.model.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.Confluence;
import org.jetbrains.annotations.NotNull;

public class WormScarfModel extends HumanoidModel<LivingEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "worm_scarf"), "main");
    private final ModelPart bone2;
    private final ModelPart bone;

    public WormScarfModel(ModelPart root) {
        super(root);
        this.bone2 = root.getChild("bone2");
        this.bone = root.getChild("bone");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 0).texOffs(0, 0).addBox(0.0F, -2.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 13).addBox(-2.0F, -2.0F, -3.25F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(10, 17).addBox(0.0F, 5.0F, -4.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(10, 13).addBox(-2.0F, 4.0F, -3.25F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));


        partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -26.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}