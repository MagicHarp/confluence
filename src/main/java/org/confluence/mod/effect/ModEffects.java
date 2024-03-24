package org.confluence.mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Confluence.MODID);

    public static final RegistryObject<ManaIssueEffect> MANA_ISSUE = MOB_EFFECTS.register("mana_issue", ManaIssueEffect::new);
}
