package org.confluence.mod.integration.airhop;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public class AirHopHelper {
    public static final ResourceLocation AIR_HOP = new ResourceLocation("airhop", "air_hop");
    private static Boolean isLoaded;
    private static Method canJump;
    private static Method isSaturated;
    private static Method getAllEnchantmentLevels;
    private static Object AIR_HOPS_CAPABILITY;
    private static Method maybeGet;
    private static Method getAirHops;

    public static boolean isLoaded() {
        if (isLoaded == null) {
            isLoaded = ModList.get().isLoaded("airhop");
        }
        return isLoaded;
    }

    public static boolean notFinishJump(Player player) {
        try {
            if (getAirHops == null) {
                ClassLoader classLoader = AirHopHelper.class.getClassLoader();
                Class<?> AirHopHandler = classLoader.loadClass("fuzs.airhop.client.handler.AirHopHandler");
                canJump = AirHopHandler.getDeclaredMethod("canJump", Player.class);
                canJump.setAccessible(true);
                isSaturated = AirHopHandler.getDeclaredMethod("isSaturated", Player.class);
                isSaturated.setAccessible(true);
                getAllEnchantmentLevels = AirHopHandler.getDeclaredMethod("getAllEnchantmentLevels", Iterable.class, Enchantment.class);
                getAllEnchantmentLevels.setAccessible(true);
                Class<?> ModRegistry = classLoader.loadClass("fuzs.airhop.init.ModRegistry");
                Field airHopsCapability = ModRegistry.getDeclaredField("AIR_HOPS_CAPABILITY");
                airHopsCapability.setAccessible(true);
                AIR_HOPS_CAPABILITY = airHopsCapability.get(null);
                Class<?> ForgeCapabilityKey = classLoader.loadClass("fuzs.puzzleslib.impl.capability.data.ForgeCapabilityKey");
                maybeGet = ForgeCapabilityKey.getDeclaredMethod("maybeGet", Object.class);
                maybeGet.setAccessible(true);
                Class<?> AirHopsCapabilityImpl = classLoader.loadClass("fuzs.airhop.capability.AirHopsCapabilityImpl");
                getAirHops = AirHopsCapabilityImpl.getDeclaredMethod("getAirHops");
                getAirHops.setAccessible(true);
            }
            boolean canJump = (boolean) AirHopHelper.canJump.invoke(null, player);
            boolean isSaturated = (boolean) AirHopHelper.isSaturated.invoke(null, player);
            if (canJump && isSaturated) {
                Optional<?> optional = (Optional<?>) maybeGet.invoke(AIR_HOPS_CAPABILITY, player);
                if (optional.isPresent()) {
                    int airHops = (int) getAirHops.invoke(optional.get());
                    int allEnchantmentLevels = (int) getAllEnchantmentLevels.invoke(null, player.getArmorSlots(), ForgeRegistries.ENCHANTMENTS.getValue(AIR_HOP));
                    return airHops < allEnchantmentLevels;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
