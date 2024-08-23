package org.confluence.mod.entity;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.integration.apothic.ApothicHelper;

public final class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Confluence.MODID);

    public static final RegistryObject<Attribute> CRIT_CHANCE = ATTRIBUTES.register("crit_chance", () -> new RangedAttribute("attribute.name.generic.critical_chance", 0.0, 0.0, 10.0).setSyncable(true));

    public static Attribute getCriticalChance() {
        return ApothicHelper.isAttributesLoaded() ? ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.CRIT_CHANCE) : CRIT_CHANCE.get();
    }
}
