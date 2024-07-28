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

public class ShieldOfCthulhuModel extends HumanoidModel<LivingEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "shield_of_cthulhu"), "main");
    private final ModelPart bone3;
    private final ModelPart bone2;
    private final ModelPart bone;

    public ShieldOfCthulhuModel(ModelPart root) {
        super(root);
        this.bone3 = root.getChild("bone3");
        this.bone2 = root.getChild("bone2");
        this.bone = root.getChild("bone");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -6.0F, -5.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(40, 56).addBox(-7.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(24, 30).addBox(-7.0F, -2.0F, -6.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(0, 50).addBox(-7.0F, -5.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 11.0F, 0.0F));


        partdefinition.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.7678F, -4.3033F, -1.3526F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -6.0F, 2.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -1.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.7678F, -4.3033F, -0.2182F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-1.75F, -1.0F, 0.0F));

        partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.5F));

        partdefinition.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(17, 13).addBox(-1.0F, -5.0F, -6.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}