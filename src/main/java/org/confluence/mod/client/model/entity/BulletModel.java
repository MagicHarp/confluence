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
import org.confluence.mod.entity.bullet.BaseBulletEntity;

public class BulletModel extends EntityModel<BaseBulletEntity> {
	public static final ModelLayerLocation AMBER_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/amber_bullet"), "main");
	public static final ModelLayerLocation AMETHYST_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/amethyst_bullet"), "main");
	public static final ModelLayerLocation DIAMOND_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/diamond_bullet"), "main");
	public static final ModelLayerLocation EMERALD_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/emerald_bullet"), "main");
	public static final ModelLayerLocation FROST_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/frost_bullet"), "main");
	public static final ModelLayerLocation RUBY_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/ruby_bullet"), "main");
	public static final ModelLayerLocation SAPPHIRE_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/sapphire_bullet"), "main");
	public static final ModelLayerLocation SPARK_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/spark_bullet"), "main");
	public static final ModelLayerLocation TOPAZ_LAYER = new ModelLayerLocation(new ResourceLocation(Confluence.MODID, "bullet/topaz_bullet"), "main");

	private final ModelPart root;

	public BulletModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.ZERO);

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