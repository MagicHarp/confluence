package org.confluence.mod.item.curio.datadriven;

import org.confluence.mod.Confluence;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.HealthAndMana.IManaReduce;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.construction.IBreakSpeedBonus;
import org.confluence.mod.item.curio.fishing.IFishingPower;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.misc.ModRarity;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.objectweb.asm.Opcodes.*;

public class DataDrivenCurioGenerator {
    private static final String BASE_CURIO_ITEM = "org/confluence/mod/item/curio/BaseCurioItem";
    private final Map<Class<?>, Number[]> classMap;
    public final Class<?> dumped;

    public DataDrivenCurioGenerator(String className, String rarity, List<String> tooltips, Map<Class<?>, Number[]> classMap) {
        this.classMap = classMap;
        this.dumped = new DataDrivenClassLoader(Confluence.class.getClassLoader()).defineClass(className,
            create(className, rarity.toUpperCase(), tooltips, new HashSet<>(classMap.keySet()))
        );
    }

    private byte[] create(String className, String rarity, List<String> tooltips, Set<Class<?>> interfaces) {
        if ("EXPERT".equals(rarity)) {
            interfaces.add(ModRarity.Expert.class);
        } else if ("MASTER".equals(rarity)) {
            interfaces.add(ModRarity.Master.class);
        }
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V17, ACC_PUBLIC | ACC_SUPER, className, null, BASE_CURIO_ITEM,
            interfaces.stream().map(DataDrivenCurioGenerator::getName).toArray(String[]::new));

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
            } else if (clazz == IFishingPower.class) {
                floatValue(cw, "getFishingBonus", clazz);
            } else if (clazz == IManaReduce.class) {
                doubleValue(cw, "getManaReduce", clazz);
            } else if (clazz == IFallResistance.class) {
                intValue(cw, "getFallResistance", clazz);
            } else if (clazz == IJumpBoost.class) {
                doubleValue(cw, "getBoost", clazz);
            } else if (clazz == IMayFly.class) {
                Number[] numbers = classMap.get(clazz);
                intValue(cw, "getFlyTicks", numbers[0].intValue());
                doubleValue(cw, "getFlySpeed", numbers[1].doubleValue());
                // todo boolValue
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

    public BaseCurioItem newInstance() {
        try {
            return (BaseCurioItem) dumped.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return new ErrorDumpedCurio();
        }
    }

    private static String getName(Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }
}
