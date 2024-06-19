package org.confluence.mod.item.curio.datadriven;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import net.minecraft.util.GsonHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.curio.HealthAndMana.IAutoGetMana;
import org.confluence.mod.item.curio.HealthAndMana.IManaReduce;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.construction.IBreakSpeedBonus;
import org.confluence.mod.item.curio.fishing.IFishingPower;
import org.confluence.mod.item.curio.fishing.IHighTestFishingLine;
import org.confluence.mod.item.curio.fishing.ILavaproofFishingHook;
import org.confluence.mod.item.curio.fishing.ITackleBox;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.misc.ModTags;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public record DataDrivenCurioInfo(String id, String rarity, List<String> tooltips, Map<Class<?>, Number[]> classMap) {
    private static final ArrayList<DataDrivenCurioInfo> INFOS = new ArrayList<>();
    private static final Hashtable<String, Class<?>> MAP = new Hashtable<>();

    static {
        MAP.put("AggroAttach", IAggroAttach.class);
        MAP.put("ArmorPass", IArmorPass.class);
        MAP.put("AutoAttack", IAutoAttack.class);
        MAP.put("CriticalHit", ICriticalHit.class);
        MAP.put("FireAttack", IFireAttack.class);
        MAP.put("FireImmune", IFireImmune.class);
        MAP.put("Honeycomb", IHoneycomb.class);
        MAP.put("HurtEvasion", IHurtEvasion.class);
        MAP.put("InvulnerableTime", IInvulnerableTime.class);
        MAP.put("LavaHurtReduce", ILavaHurtReduce.class);
        MAP.put("MagicAttack", IMagicAttack.class);
        MAP.put("ProjectileAttack", IProjectileAttack.class);
        MAP.put("StarCloak", IStarCloak.class);
        MAP.put("BreakSpeedBonus", IBreakSpeedBonus.class);
        MAP.put("FishingPower", IFishingPower.class);
        MAP.put("HighTestFishingLine", IHighTestFishingLine.class);
        MAP.put("LavaproofFishingHook", ILavaproofFishingHook.class);
        MAP.put("TackleBox", ITackleBox.class);
        MAP.put("AutoGetMana", IAutoGetMana.class);
        MAP.put("ManaReduce", IManaReduce.class);
        MAP.put("FallResistance", IFallResistance.class);
        MAP.put("JumpBoost", IJumpBoost.class);
        MAP.put("MayFly", IMayFly.class);
        MAP.put("MultiJump", IMultiJump.class);
        MAP.put("OneTimeJump", IOneTimeJump.class);
        MAP.put("LavaImmune", ILavaImmune.class);
        MAP.put("MagicQuiver", IMagicQuiver.class);
        MAP.put("Scope", IScope.class);
    }

    public static ArrayList<DataDrivenCurioInfo> generatingInfos() {
        File file = Confluence.CONFIG_PATH.toFile();
        if (!file.exists()) file.mkdir();
        Path path = Confluence.CONFIG_PATH.resolve("curio.json");
        if (Files.notExists(path)) {
            try {
                Files.writeString(path, """
                    {
                        "data_driven_test": {
                            "rarity": "MASTER",
                            "tooltips": ["It's a Data Driven item", "More information will in Github Wiki"],
                            "interfaces": {
                                "AutoAttack": {},
                                "FallResistance": -1,
                                "MayFly": [32, 0.3]
                            }
                        }
                    }
                    """);
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        }
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(JsonParser.parseReader(new InputStreamReader(inputStream)), "data driven curio");
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                JsonObject context = GsonHelper.convertToJsonObject(entry.getValue(), "context");
                String rarity = GsonHelper.getAsString(context, "rarity", "WHITE").toUpperCase();
                ArrayList<String> tooltips = new ArrayList<>();
                GsonHelper.getAsJsonArray(context, "tooltips", new JsonArray()).forEach(jsonElement -> tooltips.add(GsonHelper.convertToString(jsonElement, "tooltip")));
                Hashtable<Class<?>, Number[]> classMap = new Hashtable<>();
                for (Map.Entry<String, JsonElement> entry1 : GsonHelper.getAsJsonObject(context, "interfaces").entrySet()) {
                    Class<?> clazz = MAP.get(entry1.getKey());
                    if (clazz == null) {
                        Confluence.LOGGER.error("Unknown interface {}", entry1.getKey());
                        continue;
                    }
                    Number[] numbers;
                    JsonElement value = entry1.getValue();
                    if (value.isJsonPrimitive()) {
                        numbers = new Number[]{value.getAsNumber()};
                    } else if (value.isJsonArray()) {
                        JsonArray values = GsonHelper.convertToJsonArray(value, "values");
                        numbers = new Number[values.size()];
                        for (int i = 0; i < values.size(); i++) {
                            numbers[i] = values.get(i).getAsJsonPrimitive().getAsNumber();
                        }
                    } else {
                        numbers = new Number[0];
                    }
                    classMap.put(clazz, numbers);
                }
                INFOS.add(new DataDrivenCurioInfo(entry.getKey(), rarity, tooltips, classMap));
            }
        } catch (Exception e) {
            Confluence.LOGGER.error(e.getMessage());
        }
        return INFOS;
    }

    public static void bindTags(Map<ResourceLocation, List<TagLoader.EntryWithSource>> map) {
        List<TagLoader.EntryWithSource> list = map.computeIfAbsent(ModTags.Items.CURIO.location(), location -> new ArrayList<>());
        INFOS.forEach(info -> {
            ResourceLocation location = new ResourceLocation(Confluence.MODID, info.id.toLowerCase());
            if (list.stream().noneMatch(entryWithSource -> entryWithSource.entry().getId().equals(location))) {
                list.add(new TagLoader.EntryWithSource(TagEntry.element(location), "data_driven"));
            }
        });
        INFOS.clear();
    }
}
