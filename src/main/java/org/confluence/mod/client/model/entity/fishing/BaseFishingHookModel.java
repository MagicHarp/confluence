package org.confluence.mod.client.model.entity.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.entity.fishing.BaseFishingHook;

import static org.confluence.mod.Confluence.MODID;

public class BaseFishingHookModel extends EntityModel<BaseFishingHook> {
    public static final ModelLayerLocation WOOD = new ModelLayerLocation(new ResourceLocation(MODID, "base_fishing_hook"), "wood");
    public static final ModelLayerLocation REINFORCED = new ModelLayerLocation(new ResourceLocation(MODID, "base_fishing_hook"), "reinforced");
    public static final ModelLayerLocation SOULS = new ModelLayerLocation(new ResourceLocation(MODID, "base_fishing_hook"), "souls");
    public static final ModelLayerLocation FLESH = new ModelLayerLocation(new ResourceLocation(MODID, "base_fishing_hook"), "flesh");
    public static final ModelLayerLocation SCARAB = new ModelLayerLocation(new ResourceLocation(MODID, "base_fishing_hook"), "scarab");
    private final ModelPart bb_main;

    public BaseFishingHookModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createWoodLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(0, 6).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE), PartPose.ZERO);
        bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -1.5F, -2.0F, 0.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.75F, 3.5F, -2.5F, 0.1745F, -0.3054F, 0.2182F));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public static LayerDefinition createReinforcedLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 7).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(0, 10).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(0, 0).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public static LayerDefinition createSoulsLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(12, 0).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(0, 11).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -2.3562F, 0.0F));
        bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public static LayerDefinition createFleshLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(14, 2).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(12, 0).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.5F, -3.5F, 0.0F, 7.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, 3.1416F, 0.0F));
        bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -4.5F, -3.5F, 0.0F, 7.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -2.3562F, 0.0F));
        bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -4.5F, -3.5F, 0.0F, 7.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public static LayerDefinition createScarabLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(14, 10).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE)
            .texOffs(0, 14).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE)
            .texOffs(0, 0).addBox(-2.5F, 0.5F, -2.5F, 5.0F, 6.0F, 4.0F, new CubeDeformation(-0.75F))
            .texOffs(0, 10).addBox(-3.5F, 0.5F, -0.5F, 7.0F, 7.0F, 0.0F, CubeDeformation.NONE), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(BaseFishingHook entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}