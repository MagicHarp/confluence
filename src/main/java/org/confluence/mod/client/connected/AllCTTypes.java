package org.confluence.mod.client.connected;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.connected.behaviour.ConnectedTextureBehaviour;

import java.util.Locale;

public enum AllCTTypes implements CTType {
	HORIZONTAL(2, ConnectedTextureBehaviour.ContextRequirement.builder().horizontal().build()) {
		@Override
		public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
			return (context.right ? 1 : 0) + (context.left ? 2 : 0);
		}
	},
	HORIZONTAL_KRYPPERS(2, ConnectedTextureBehaviour.ContextRequirement.builder().horizontal().build()) {
		@Override
		public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
			return !context.right && !context.left ? 0 : !context.right ? 3 : !context.left ? 2 : 1;
		}
	},
	VERTICAL(2, ConnectedTextureBehaviour.ContextRequirement.builder().vertical().build()) {
		@Override
		public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
			return (context.up ? 1 : 0) + (context.down ? 2 : 0);
		}
	},
	OMNIDIRECTIONAL(8, ConnectedTextureBehaviour.ContextRequirement.builder().all().build()) {
		@Override
		public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
			ConnectedTextureBehaviour.CTContext c = context;
			int tileX = 0, tileY = 0;
			int borders = (!c.up ? 1 : 0) + (!c.down ? 1 : 0) + (!c.left ? 1 : 0) + (!c.right ? 1 : 0);

			if (c.up)
				tileX++;
			if (c.down)
				tileX += 2;
			if (c.left)
				tileY++;
			if (c.right)
				tileY += 2;

			if (borders == 0) {
				if (c.topRight)
					tileX++;
				if (c.topLeft)
					tileX += 2;
				if (c.bottomRight)
					tileY += 2;
				if (c.bottomLeft)
					tileY++;
			}

			if (borders == 1) {
				if (!c.right) {
					if (c.topLeft || c.bottomLeft) {
						tileY = 4;
						tileX = -1 + (c.bottomLeft ? 1 : 0) + (c.topLeft ? 1 : 0) * 2;
					}
				}
				if (!c.left) {
					if (c.topRight || c.bottomRight) {
						tileY = 5;
						tileX = -1 + (c.bottomRight ? 1 : 0) + (c.topRight ? 1 : 0) * 2;
					}
				}
				if (!c.down) {
					if (c.topLeft || c.topRight) {
						tileY = 6;
						tileX = -1 + (c.topLeft ? 1 : 0) + (c.topRight ? 1 : 0) * 2;
					}
				}
				if (!c.up) {
					if (c.bottomLeft || c.bottomRight) {
						tileY = 7;
						tileX = -1 + (c.bottomLeft ? 1 : 0) + (c.bottomRight ? 1 : 0) * 2;
					}
				}
			}

			if (borders == 2) {
				if ((c.up && c.left && c.topLeft) || (c.down && c.left && c.bottomLeft)
					|| (c.up && c.right && c.topRight) || (c.down && c.right && c.bottomRight))
					tileX += 3;
			}

			return tileX + 8 * tileY;
		}
	},
	CROSS(4, ConnectedTextureBehaviour.ContextRequirement.builder().axisAligned().build()) {
		@Override
		public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
			return (context.up ? 1 : 0) + (context.down ? 2 : 0) + (context.left ? 4 : 0) + (context.right ? 8 : 0);
		}
	},
	RECTANGLE(4, ConnectedTextureBehaviour.ContextRequirement.builder().axisAligned().build()) {
		@Override
		public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
			int x = context.left && context.right ? 2 : context.left ? 3 : context.right ? 1 : 0;
			int y = context.up && context.down ? 1 : context.up ? 2 : context.down ? 0 : 3;
			return x + y * 4;
		}
	};

	private final ResourceLocation id;
	private final int sheetSize;
	private final ConnectedTextureBehaviour.ContextRequirement contextRequirement;

	AllCTTypes(int sheetSize, ConnectedTextureBehaviour.ContextRequirement contextRequirement) {
		this.id = new ResourceLocation(Confluence.MODID, name().toLowerCase(Locale.ROOT));
		this.sheetSize = sheetSize;
		this.contextRequirement = contextRequirement;

		CTTypeRegistry.register(this);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public int getSheetSize() {
		return sheetSize;
	}

	@Override
	public ConnectedTextureBehaviour.ContextRequirement getContextRequirement() {
		return contextRequirement;
	}
}
