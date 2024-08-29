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
import org.confluence.mod.item.curio.fishing.IFishingPower;
import org.confluence.mod.item.curio.fishing.IHighTestFishingLine;
import org.confluence.mod.item.curio.fishing.ILavaproofFishingHook;
import org.confluence.mod.item.curio.fishing.ITackleBox;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IWallClimb;
import top.theillusivec4.curios.Curios;

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
        MAP.put("FireAttack", IFireAttack.class);
        MAP.put("FireImmune", IFireImmune.class);
        MAP.put("Honeycomb", IHoneycomb.class);
        MAP.put("InvulnerableTime", IInvulnerableTime.class);
        MAP.put("LavaHurtReduce", ILavaHurtReduce.class);
        MAP.put("MagicAttack", IMagicAttack.class);
        MAP.put("StarCloak", IStarCloak.class);
        MAP.put("FishingPower", IFishingPower.class);
        MAP.put("HighTestFishingLine", IHighTestFishingLine.class);
        MAP.put("LavaproofFishingHook", ILavaproofFishingHook.class);
        MAP.put("TackleBox", ITackleBox.class);
        MAP.put("AutoGetMana", IAutoGetMana.class);
        MAP.put("ManaReduce", IManaReduce.class);
        MAP.put("FallResistance", IFallResistance.class);
        MAP.put("JumpBoost", IJumpBoost.class);
        MAP.put("MayFly", IMayFly.class);
        //MAP.put("MultiJump", IMultiJump.class);
        //MAP.put("OneTimeJump", IOneTimeJump.class);
        MAP.put("LavaImmune", ILavaImmune.class);
        MAP.put("MagicQuiver", IMagicQuiver.class);
        MAP.put("Scope", IScope.class);
        MAP.put("WallClimb", IWallClimb.class);
    }

    public static ArrayList<DataDrivenCurioInfo> generatingInfos() {
        Path path = Confluence.CONFIG_PATH.resolve("curio.json");
        if (Files.notExists(path)) return new ArrayList<>(); // 不生成文件，防止卡审核

        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(JsonParser.parseReader(new InputStreamReader(inputStream)), "data_driven");
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
                    if (clazz == null) {
                        Confluence.LOGGER.warn("Unknown interface: {}", entry1.getKey());
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
    }
}
