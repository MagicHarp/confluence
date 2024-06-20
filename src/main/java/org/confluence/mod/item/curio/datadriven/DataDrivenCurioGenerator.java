package org.confluence.mod.item.curio.datadriven;

import org.confluence.mod.Confluence;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.construction.IBreakSpeedBonus;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.misc.ModRarity;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.*;
import java.util.stream.Collectors;

import static org.objectweb.asm.Opcodes.*;

public class DataDrivenCurioGenerator {
    private static final String BASE_CURIO_ITEM = "org/confluence/mod/item/curio/BaseCurioItem";
    private final Map<Class<?>, Number[]> classMap;
    public final Class<?> dumped;

    public DataDrivenCurioGenerator(DataDrivenCurioInfo info) {
        this.classMap = info.classMap();
        HashSet<Class<?>> interfaces = new HashSet<>(classMap.keySet());
        interfaces.add(CustomModel.class);
        String rarity = info.rarity().toUpperCase();
        if ("EXPERT".equals(rarity)) {
            interfaces.add(ModRarity.Expert.class);
        } else if ("MASTER".equals(rarity)) {
            interfaces.add(ModRarity.Master.class);
        }
        String className = Arrays.stream(info.id().split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining());
        this.dumped = new DataDrivenClassLoader(Confluence.class.getClassLoader()).defineClass(
            className, create(className, rarity, info.tooltips(), info.infos(), interfaces)
        );
    }

    private byte[] create(String className, String rarity, List<String> tooltips, ArrayList<DataDrivenAttributeInfo> infos, Set<Class<?>> interfaces) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V17, ACC_PUBLIC | ACC_SUPER, className, null, BASE_CURIO_ITEM, interfaces.stream().map(DataDrivenCurioGenerator::getName).toArray(String[]::new));

        if (!infos.isEmpty()) {
            cw.visitInnerClass("com/google/common/collect/ImmutableMultimap$Builder", "com/google/common/collect/ImmutableMultimap", "Builder", ACC_PUBLIC | ACC_STATIC);

            cw.visitInnerClass("net/minecraft/world/entity/ai/attributes/AttributeModifier$Operation", "net/minecraft/world/entity/ai/attributes/AttributeModifier", "Operation", ACC_PUBLIC | ACC_FINAL | ACC_STATIC | ACC_ENUM);

            FieldVisitor fv = cw.visitField(ACC_PRIVATE | ACC_FINAL | ACC_STATIC, "ATTRIBUTE", "Lcom/google/common/collect/ImmutableMultimap;", "Lcom/google/common/collect/ImmutableMultimap<Lnet/minecraft/world/entity/ai/attributes/Attribute;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;>;", null);
            fv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, "org/confluence/mod/misc/ModRarity", rarity, "Lnet/minecraft/world/item/Rarity;");
            mv.visitMethodInsn(INVOKESPECIAL, BASE_CURIO_ITEM, "<init>", "(Lnet/minecraft/world/item/Rarity;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        for (Class<?> clazz : interfaces) {
            if (clazz == IAggroAttach.class) {
                intValue(cw, "getAggro", clazz);
            } else if (clazz == IArmorPass.class) {
                intValue(cw, "getPassValue", clazz);
            } else if (clazz == ICriticalHit.class) {
                doubleValue(cw, "getChance", clazz);
            } else if (clazz == IInvulnerableTime.class) {
                intValue(cw, "getTime", clazz);
            } else if (clazz == IMagicAttack.class) {
                doubleValue(cw, "getMagicBonus", clazz);
            } else if (clazz == IProjectileAttack.class) {
                floatValue(cw, "getProjectileBonus", clazz);
            } else if (clazz == IBreakSpeedBonus.class) {
                floatValue(cw, "getBreakBonus", clazz);
            } else if (clazz == IFallResistance.class) {
                intValue(cw, "getFallResistance", clazz);
            } else if (clazz == IJumpBoost.class) {
                doubleValue(cw, "getBoost", clazz);
            } else if (clazz == IMayFly.class) {
                Number[] numbers = classMap.get(clazz);
                intValue(cw, "getFlyTicks", numbers[0].intValue());
                doubleValue(cw, "getFlySpeed", numbers[1].doubleValue());
            } else if (clazz == IMultiJump.class) {
                doubleValue(cw, "getJumpSpeed", clazz);
            } else if (clazz == IOneTimeJump.class) {
                Number[] numbers = classMap.get(clazz);
                intValue(cw, "getJumpTicks", numbers[0].intValue());
                doubleValue(cw, "getJumpSpeed", numbers[1].doubleValue());
            }
        }
        if (!tooltips.isEmpty()) {
            appendTooltip(cw, tooltips);
        }
        if (!infos.isEmpty()) {
            appendAttribute(cw, className);
            clinit(cw, className, infos);
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    private void intValue(ClassWriter cw, String methodName, Class<?> clazz) {
        intValue(cw, methodName, classMap.get(clazz)[0].intValue());
    }

    private void intValue(ClassWriter cw, String methodName, int value) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, methodName, "()I", null, null);
        mv.visitCode();
        mv.visitIntInsn(SIPUSH, value);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private void doubleValue(ClassWriter cw, String methodName, Class<?> clazz) {
        doubleValue(cw, methodName, classMap.get(clazz)[0].doubleValue());
    }

    private void doubleValue(ClassWriter cw, String methodName, double value) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, methodName, "()D", null, null);
        mv.visitCode();
        mv.visitLdcInsn(value);
        mv.visitInsn(DRETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }

    private void floatValue(ClassWriter cw, String methodName, Class<?> clazz) {
        floatValue(cw, methodName, classMap.get(clazz)[0].floatValue());
    }

    private void floatValue(ClassWriter cw, String methodName, float value) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, methodName, "()F", null, null);
        mv.visitCode();
        mv.visitLdcInsn(value);
        mv.visitInsn(FRETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }

    private void appendTooltip(ClassWriter cw, List<String> tooltips) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "appendHoverText", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List<Lnet/minecraft/network/chat/Component;>;Lnet/minecraft/world/item/TooltipFlag;)V", null);
        mv.visitCode();
        for (String tooltip : tooltips) {
            mv.visitVarInsn(ALOAD, 3);
            mv.visitLdcInsn(tooltip);
            mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/network/chat/Component", "translatable", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", true);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 5);
        mv.visitEnd();
    }

    private void appendAttribute(ClassWriter cw, String className) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "getAttributeModifiers", "(Ltop/theillusivec4/curios/api/SlotContext;Ljava/util/UUID;Lnet/minecraft/world/item/ItemStack;)Lcom/google/common/collect/Multimap;", "(Ltop/theillusivec4/curios/api/SlotContext;Ljava/util/UUID;Lnet/minecraft/world/item/ItemStack;)Lcom/google/common/collect/Multimap<Lnet/minecraft/world/entity/ai/attributes/Attribute;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;>;", null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, className, "ATTRIBUTE", "Lcom/google/common/collect/ImmutableMultimap;");
        mv.visitInsn(ARETURN);
        mv.visitMaxs(1, 4);
        mv.visitEnd();
    }

    private void clinit(ClassWriter cw, String className, List<DataDrivenAttributeInfo> infos) {
        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        mv.visitCode();

        mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/ImmutableMultimap", "builder", "()Lcom/google/common/collect/ImmutableMultimap$Builder;", false);
        for (DataDrivenAttributeInfo info : infos) {
            mv.visitFieldInsn(GETSTATIC, "net/minecraftforge/registries/ForgeRegistries", "ATTRIBUTES", "Lnet/minecraftforge/registries/IForgeRegistry;");
            mv.visitTypeInsn(NEW, "net/minecraft/resources/ResourceLocation");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(info.namespace());
            mv.visitLdcInsn(info.path());
            mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/resources/ResourceLocation", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraftforge/registries/IForgeRegistry", "getValue", "(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "net/minecraft/world/entity/ai/attributes/Attribute");
            mv.visitTypeInsn(NEW, "net/minecraft/world/entity/ai/attributes/AttributeModifier");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(info.uuid());
            mv.visitMethodInsn(INVOKESTATIC, "java/util/UUID", "fromString", "(Ljava/lang/String;)Ljava/util/UUID;", false);
            mv.visitLdcInsn(info.name());
            mv.visitLdcInsn(info.value());
            mv.visitFieldInsn(GETSTATIC, "net/minecraft/world/entity/ai/attributes/AttributeModifier$Operation", info.operation(), "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;");
            mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/world/entity/ai/attributes/AttributeModifier", "<init>", "(Ljava/util/UUID;Ljava/lang/String;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/google/common/collect/ImmutableMultimap$Builder", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMultimap$Builder;", false);
        }
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/google/common/collect/ImmutableMultimap$Builder", "build", "()Lcom/google/common/collect/ImmutableMultimap;", false);
        mv.visitFieldInsn(PUTSTATIC, className, "ATTRIBUTE", "Lcom/google/common/collect/ImmutableMultimap;");

        mv.visitInsn(RETURN);
        mv.visitMaxs(9, 0);
        mv.visitEnd();
    }

    public BaseCurioItem newInstance() {
        try {
            return (BaseCurioItem) dumped.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorDumpedCurio();
        }
    }

    private static String getName(Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }
}
