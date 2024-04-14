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

public class TerrasparkBootsModel extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "terraspark_boots"), "main");

	public TerrasparkBootsModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group2 = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 4).addBox(-8.0F, -2.0F, 5.5F, 4.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(12, 15).addBox(-8.0F, -2.0F, 4.25F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(14, 7).addBox(-8.5F, -3.875F, 5.5F, 5.0F, 1.0F, 3.0F, CubeDeformation.NONE)
		.texOffs(12, 0).addBox(-8.5F, -5.125F, 5.5F, 5.0F, 1.0F, 3.0F, CubeDeformation.NONE)
		.texOffs(0, 10).addBox(-8.5F, -6.375F, 5.25F, 5.0F, 1.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(0, 15).addBox(-8.5F, -7.375F, 5.25F, 5.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 0).addBox(-8.0F, -6.0F, 6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		group2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 17).addBox(1.875F, 0.0F, 1.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-6.25F, -4.4749F, 7.2315F, 0.3927F, 0.0F, 0.0F));

		group2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(1.875F, 1.25F, 1.25F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-6.25F, -4.4754F, 6.9841F, 0.3927F, 0.0F, 0.0F));

		group2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 11).addBox(-2.5F, 1.5F, -1.0F, 5.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-6.0F, -3.375F, 7.25F, -0.7854F, 0.0F, 0.0F));

		PartDefinition group = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 4).mirror().addBox(-12.0F, -2.0F, 5.5F, 4.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(12, 15).mirror().addBox(-12.0F, -2.0F, 4.25F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(14, 7).mirror().addBox(-12.5F, -3.875F, 5.5F, 5.0F, 1.0F, 3.0F, CubeDeformation.NONE)
		.texOffs(12, 0).mirror().addBox(-12.5F, -5.125F, 5.5F, 5.0F, 1.0F, 3.0F, CubeDeformation.NONE)
		.texOffs(0, 10).mirror().addBox(-12.5F, -6.375F, 5.25F, 5.0F, 1.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(0, 15).mirror().addBox(-12.5F, -7.375F, 5.25F, 5.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 0).mirror().addBox(-12.0F, -6.0F, 6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		group.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-2.875F, 0.0F, 1.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-9.75F, -4.4749F, 7.2315F, 0.3927F, 0.0F, 0.0F));

		group.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.875F, 1.25F, 1.25F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-9.75F, -4.4754F, 6.9841F, 0.3927F, 0.0F, 0.0F));

		group.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(14, 11).mirror().addBox(-2.5F, 1.5F, -1.0F, 5.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-10.0F, -3.375F, 7.25F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}