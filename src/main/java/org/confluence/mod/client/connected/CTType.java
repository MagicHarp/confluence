package org.confluence.mod.client.connected;

import net.minecraft.resources.ResourceLocation;

public interface CTType {
	ResourceLocation getId();

	int getSheetSize();

	ConnectedTextureBehaviour.ContextRequirement getContextRequirement();

	int getTextureIndex(ConnectedTextureBehaviour.CTContext context);
}
