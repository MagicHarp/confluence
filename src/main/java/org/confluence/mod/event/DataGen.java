package org.confluence.mod.event;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.util.ConfluenceChinese;
import org.confluence.mod.util.ConfluenceEnglish;
import org.confluence.mod.util.ConfluenceItemModels;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean bool = event.includeClient();
        // Initialize by generate chinese
        generator.addProvider(bool, new ConfluenceChinese(output));
        generator.addProvider(bool, new ConfluenceEnglish(output));
        generator.addProvider(bool, new ConfluenceItemModels(output, helper));
    }
}
