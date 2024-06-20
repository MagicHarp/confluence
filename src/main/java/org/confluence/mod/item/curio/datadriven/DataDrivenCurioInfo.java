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
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.construction.IBreakSpeedBonus;
import org.confluence.mod.item.curio.movement.*;
import top.theillusivec4.curios.Curios;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public record DataDrivenCurioInfo(String id, String rarity, List<String> tooltips, String slot, ArrayList<DataDrivenAttributeInfo> infos, Map<Class<?>, Number[]> classMap) {
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
                            "tooltips": [
                                "It's a Data Driven curio",
                                "More information in Github Wiki"
                            ],
                            "slot": "curio",
                            "attributes": {
                                "minecraft:generic.movement_speed": {
                                    "uuid": "DC2CE9B0-2637-329F-2E1F-998F1A8FA5A1",
                                    "name": "Data Driven",
                                    "value": 0.05,
                                    "operation": "MULTIPLY_TOTAL"
                                }
                            },
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
                String id = entry.getKey();
                JsonObject context = GsonHelper.convertToJsonObject(entry.getValue(), "context");
                String rarity = GsonHelper.getAsString(context, "rarity", "WHITE").toUpperCase();
                ArrayList<String> tooltips = new ArrayList<>();
                GsonHelper.getAsJsonArray(context, "tooltips", new JsonArray()).forEach(jsonElement -> tooltips.add(GsonHelper.convertToString(jsonElement, "tooltip")));
                String slot = GsonHelper.getAsString(context, "slot", "curio");
                ArrayList<DataDrivenAttributeInfo> infos = new ArrayList<>();
                for (Map.Entry<String, JsonElement> entry1 : GsonHelper.getAsJsonObject(context, "attributes", new JsonObject()).entrySet()) {
                    ResourceLocation location = new ResourceLocation(entry1.getKey());
                    JsonObject attribute = GsonHelper.convertToJsonObject(entry1.getValue(), "attribute");
                    String uuid = GsonHelper.getAsString(attribute, "uuid", UUID.nameUUIDFromBytes((id + infos.size()).getBytes()).toString());
                    String name = GsonHelper.getAsString(attribute, "name", id);
                    double value = GsonHelper.getAsDouble(attribute, "value", 1.0);
                    String operation = GsonHelper.getAsString(attribute, "operation", "ADDITION").toUpperCase();
                    infos.add(new DataDrivenAttributeInfo(location.getNamespace(), location.getPath(), uuid, name, value, operation));
                }
                Hashtable<Class<?>, Number[]> classMap = new Hashtable<>();
                for (Map.Entry<String, JsonElement> entry1 : GsonHelper.getAsJsonObject(context, "interfaces", new JsonObject()).entrySet()) {
                    Class<?> clazz = MAP.get(entry1.getKey());
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
                INFOS.add(new DataDrivenCurioInfo(id, rarity, tooltips, slot, infos, classMap));
            }
        } catch (Exception e) {
            Confluence.LOGGER.error(e.getMessage());
        }
        return INFOS;
    }

    public static void bindTags(Map<ResourceLocation, List<TagLoader.EntryWithSource>> map) {
        INFOS.forEach(info -> {
            List<TagLoader.EntryWithSource> list = map.computeIfAbsent(new ResourceLocation(Curios.MODID, info.slot), location -> new ArrayList<>());
            ResourceLocation location = new ResourceLocation(Confluence.MODID, info.id.toLowerCase());
            if (list.stream().noneMatch(entryWithSource -> entryWithSource.entry().getId().equals(location))) {
                list.add(new TagLoader.EntryWithSource(TagEntry.element(location), "data_driven"));
            }
        });
        INFOS.clear();
    }
}
