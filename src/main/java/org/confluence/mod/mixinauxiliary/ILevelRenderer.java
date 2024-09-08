package org.confluence.mod.mixinauxiliary;

import net.minecraftforge.fml.loading.FMLLoader;

public interface ILevelRenderer {
    boolean isRubidiumLoaded = FMLLoader.getLoadingModList().getModFileById("rubidium") != null;

    void confluence$rebuildAllChunks();
}
