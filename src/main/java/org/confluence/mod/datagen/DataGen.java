package org.confluence.mod.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean bool = event.includeClient();
        generator.addProvider(bool, new ConfluenceChineseProvider(output));
        generator.addProvider(bool, new ConfluenceEnglishProvider(output));
        generator.addProvider(bool, new ConfluenceItemModelProvider(output, helper));
        generator.addProvider(bool, new ConfluenceBlockStateProvider(output, helper));
    }
}
