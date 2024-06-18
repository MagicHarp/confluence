package org.confluence.mod.item.curio.datadriven;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.GsonHelper;
import org.confluence.mod.Confluence;

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
    private static final Hashtable<String, String> MAP = new Hashtable<>();

    static {
        MAP.put("AggroAttach", "org.confluence.mod.item.curio.combat.IAggroAttach");
        MAP.put("ArmorPass", "org.confluence.mod.item.curio.combat.IArmorPass");
        MAP.put("AutoAttack", "org.confluence.mod.item.curio.combat.IAutoAttack");
        MAP.put("CriticalHit", "org.confluence.mod.item.curio.combat.ICriticalHit");
        MAP.put("FireAttack", "org.confluence.mod.item.curio.combat.IFireAttack");
        MAP.put("FireImmune", "org.confluence.mod.item.curio.combat.IFireImmune");
        MAP.put("Honeycomb", "org.confluence.mod.item.curio.combat.IHoneycomb");
        MAP.put("HurtEvasion", "org.confluence.mod.item.curio.combat.IHurtEvasion");
        MAP.put("InvulnerableTime", "org.confluence.mod.item.curio.combat.IInvulnerableTime");
        MAP.put("LavaHurtReduce", "org.confluence.mod.item.curio.combat.ILavaHurtReduce");
        MAP.put("MagicAttack", "org.confluence.mod.item.curio.combat.IMagicAttack");
        MAP.put("ProjectileAttack", "org.confluence.mod.item.curio.combat.IProjectileAttack");
        MAP.put("StarCloak", "org.confluence.mod.item.curio.combat.IStarCloak");
        MAP.put("BreakSpeedBonus", "org.confluence.mod.item.curio.construction.IBreakSpeedBonus");
        MAP.put("FishingPower", "org.confluence.mod.item.curio.fishing.IFishingPower");
        MAP.put("HighTestFishingLine", "org.confluence.mod.item.curio.fishing.IHighTestFishingLine");
        MAP.put("LavaproofFishingHook", "org.confluence.mod.item.curio.fishing.ILavaproofFishingHook");
        MAP.put("TackleBox", "org.confluence.mod.item.curio.fishing.ITackleBox");
        MAP.put("AutoGetMana", "org.confluence.mod.item.curio.HealthAndMana.IAutoGetMana");
        MAP.put("ManaReduce", "org.confluence.mod.item.curio.HealthAndMana.IManaReduce");
        MAP.put("FallResistance", "org.confluence.mod.item.curio.movement.IFallResistance");
        MAP.put("JumpBoost", "org.confluence.mod.item.curio.movement.IJumpBoost");
        MAP.put("MayFly", "org.confluence.mod.item.curio.movement.IMayFly");
        MAP.put("MultiJump", "org.confluence.mod.item.curio.movement.IMultiJump");
        MAP.put("OneTimeJump", "org.confluence.mod.item.curio.movement.IOneTimeJump");
        MAP.put("LavaImmune", "org.confluence.mod.item.curio.ILavaImmune");
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
                            "rarity": "PURPLE",
                            "tooltips": ["It's a Data Driven item", "Hi from Confluence Team!"],
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
                JsonObject attributes = GsonHelper.convertToJsonObject(entry.getValue(), "attributes");
                String rarity = GsonHelper.getAsString(attributes, "rarity", "WHITE").toUpperCase();
                ArrayList<String> tooltips = new ArrayList<>();
                GsonHelper.getAsJsonArray(attributes, "tooltips", new JsonArray()).forEach(jsonElement ->
                    tooltips.add(GsonHelper.convertToString(jsonElement, "tooltip")));
                Hashtable<Class<?>, Number[]> classMap = new Hashtable<>();
                ClassLoader classLoader = Confluence.class.getClassLoader();
                for (Map.Entry<String, JsonElement> entry1 : GsonHelper.getAsJsonObject(attributes, "interfaces").entrySet()) {
                    Class<?> clazz = classLoader.loadClass(MAP.get(entry1.getKey()));
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
}
