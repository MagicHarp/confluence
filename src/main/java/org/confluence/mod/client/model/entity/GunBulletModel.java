package org.confluence.mod.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.projectile.BaseBulletEntity;

public class GunBulletModel extends EntityModel<BaseBulletEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "gun_bullet"), "main");

	private final ModelPart root;

	public GunBulletModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -10.0F, -4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(1.0F, -10.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-1.0F, -10.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(BaseBulletEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}