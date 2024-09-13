package org.confluence.mod.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;
import org.jetbrains.annotations.NotNull;

public class AmmoModel extends EntityModel<BaseAmmoEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Confluence.asResource("base_ammo"), "main");
	private final ModelPart bb_main;

	public AmmoModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 15.0F, CubeDeformation.NONE)
			.texOffs(0, 16).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 12.0F, CubeDeformation.NONE), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull BaseAmmoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}