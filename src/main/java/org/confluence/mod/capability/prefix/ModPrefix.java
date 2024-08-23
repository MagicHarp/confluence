package org.confluence.mod.capability.prefix;

import net.minecraftforge.common.IExtensibleEnum;

/**
 * After your extending, don't forget to invoke {@link PrefixType#updatePrefix()}
 */
@SuppressWarnings("unused")
public interface ModPrefix extends IExtensibleEnum {
    void copyTo(ItemPrefix itemPrefix);

    boolean isHarmful();

    enum Curio implements ModPrefix {
        HARD(1, 0, 0, 0, 0, 0, 1, 0.1025F), // 坚硬
        GUARDING(2, 0, 0, 0, 0, 0, 1, 0.21F), // 守护
        ARMORED(3, 0, 0, 0, 0, 0, 1, 0.3225F), // 装甲
        WARDING(4, 0, 0, 0, 0, 0, 2, 0.44F), // 护佑

        PRECISE(0, 0.02F, 0, 0, 0, 0, 1, 0.21F), // 精确
        LUCKY(0, 0.04F, 0, 0, 0, 0, 2, 0.44F), // 幸运

        JAGGED(0, 0, 0.01F, 0, 0, 0, 1, 0.1025F), // 锯齿
        SPIKED(0, 0, 0.02F, 0, 0, 0, 1, 0.21F), // 尖刺
        ANGRY(0, 0, 0.03F, 0, 0, 0, 1, 0.3225F), // 愤怒
        MENACING(0, 0, 0.04F, 0, 0, 0, 2, 0.44F), // 险恶

        WILD(0, 0, 0, 0.01F, 0, 0, 1, 0.1025F), // 狂野
        RASH(0, 0, 0, 0.02F, 0, 0, 1, 0.21F), // 鲁莽
        INTREPID(0, 0, 0, 0.03F, 0, 0, 1, 0.3225F), // 勇猛
        VIOLENT(0, 0, 0, 0.04F, 0, 0, 2, 0.44F), // 暴力

        BRISK(0, 0, 0, 0, 0.01F, 0, 1, 0.1025F), // 轻快
        FLEETING(0, 0, 0, 0, 0.02F, 0, 1, 0.21F), // 快速
        HASTY(0, 0, 0, 0, 0.03F, 0, 1, 0.3225F), // 急速
        QUICK(0, 0, 0, 0, 0.04F, 0, 2, 0.44F), // 迅捷

        ARCANE(0, 0, 0, 0, 0, 20, 1, 0.3225F); // 奥秘

        public final int armor;
        public final float criticalChance;
        public final float attackDamage;
        public final float attackSpeed;
        public final float movementSpeed;
        public final int additionalMana;
        public final int tier;
        public final float value;

        Curio(int armor, float criticalChance, float attackDamage, float attackSpeed, float movementSpeed, int additionalMana, int tier, float value) {
            this.armor = armor;
            this.criticalChance = criticalChance;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.movementSpeed = movementSpeed;
            this.additionalMana = additionalMana;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name().toLowerCase();
            itemPrefix.armor = armor;
            itemPrefix.criticalChance = criticalChance;
            itemPrefix.attackDamage = attackDamage;
            itemPrefix.attackSpeed = attackSpeed;
            itemPrefix.movementSpeed = movementSpeed;
            itemPrefix.additionalMana = additionalMana;
            itemPrefix.tier = tier;
            itemPrefix.value = value;
        }

        @Override
        public boolean isHarmful() {
            return false;
        }

        public static Curio create(String name, int armor, float criticalChance, float attackDamage, float attackSpeed, float movementSpeed, int additionalMana, int tier, float value) {
            throw new IllegalStateException("Enum not extended");
        }
    }

    enum Universal implements ModPrefix {
        KEEN(0, 0.03F, 0, 1, 0.1236F), // 锐利
        SUPERIOR(0.1F, 0.03F, 0.1F, 2, 0.6451F), // 高端
        FORCEFUL(0, 0, 0.15F, 1, 0.3225F), // 强力
        BROKEN(-0.3F, 0, -0.2F, -2, -0.6864F), // 碎裂
        DAMAGED(-0.15F, 0, 0, -1, -0.2775F), // 破损
        SHODDY(-0.1F, 0, -0.15F, -2, -0.4148F), // 粗劣
        HURTFUL(0.1F, 0, 0, 1, 0.21F), // 致伤
        STRONG(0, 0, 0.15F, 1, 0.21F), // 强劲
        UNPLEASANT(0.05F, 0, 0.15F, 2, 0.4581F), // 粗鲁
        WEAK(0, 0, -0.2F, -2, -0.36F), // 虚弱
        RUTHLESS(0.18F, 0, -0.1F, 1, 0.1278F), // 无情
        GODLY(0.15F, 0.05F, 0.15F, 2, 1.1163F), // 神级
        DEMONIC(0.15F, 0.05F, 0, 2, 0.6002F), // 恶魔
        ZEALOUS(0, 0.05F, 0, 1, 0.21F); // 狂热

        public final float attackDamage;
        public final float criticalChance;
        public final float knockBack;
        public final int tier;
        public final float value;

        Universal(float attackDamage, float criticalChance, float knockBack, int tier, float value) {
            this.attackDamage = attackDamage;
            this.criticalChance = criticalChance;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name().toLowerCase();
            itemPrefix.attackDamage = attackDamage;
            itemPrefix.criticalChance = criticalChance;
            itemPrefix.knockBack = knockBack;
            itemPrefix.tier = tier;
            itemPrefix.value = value;
        }

        @Override
        public boolean isHarmful() {
            return tier < 0;
        }

        public static Universal create(String name, float attackDamage, float criticalChance, float knockBack, int tier, float value) {
            throw new IllegalStateException("Enum not extended");
        }
    }

    enum Common implements ModPrefix {
        QUICK(0, 0.1F, 0, 0, 1, 0.21F), // 迅捷
        DEADLY(0.1F, 0.1F, 0, 0, 2, 0.4641F), // 致命
        AGILE(0, 0.1F, 0.03F, 0, 1, 0.3596F), // 灵活
        NIMBLE(0, 0.05F, 0, 0, 1, 0.1025F), // 灵巧
        MURDEROUS(0.07F, 0.06F, 0.03F, 0, 2, 0.4454F), // 残暴
        SLOW(0, -0.15F, 0, 0, -1, -0.2775F), // 缓慢
        SLUGGISH(0, -0.2F, 0, 0, -2, -0.36F), // 迟钝
        LAZY(0, -0.08F, 0, 0, -1, -0.1536F), // 呆滞
        ANNOYING(-0.2F, -0.15F, 0, 0, -2, -0.5376F), // 惹恼
        NASTY(0.05F, 0.1F, 0.02F, -0.1F, 1, 0.1687F); // 凶险

        public final float attackDamage;
        public final float attackSpeed;
        public final float criticalChance;
        public final float knockBack;
        public final int tier;
        public final float value;

        Common(float attackDamage, float attackSpeed, float criticalChance, float knockBack, int tier, float value) {
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.criticalChance = criticalChance;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name().toLowerCase();
            itemPrefix.attackDamage = attackDamage;
            itemPrefix.attackSpeed = attackSpeed;
            itemPrefix.criticalChance = criticalChance;
            itemPrefix.knockBack = knockBack;
            itemPrefix.tier = tier;
            itemPrefix.value = value;
        }

        @Override
        public boolean isHarmful() {
            return tier < 0 && this != ANNOYING;
        }

        public static Common create(String name, float attackDamage, float attackSpeed, float criticalChance, float knockBack, int tier, float value) {
            throw new IllegalStateException("Enum not extended");
        }
    }

    enum Melee implements ModPrefix {
        LARGE(0, 0, 0, 0.12F, 0, 1, 0.2544F), // 大
        MASSIVE(0, 0, 0, 0.18F, 0, 1, 0.3924F), // 巨大
        DANGEROUS(0.05F, 0, 0.02F, 0.05F, 0, 1, 0.3147F), // 危险
        SAVAGE(0.1F, 0, 0, 0.1F, 0.1F, 2, 0.7716F), // 凶险
        SHARP(0.15F, 0, 0, 0, 0, 1, 0.3225F), // 锋利
        POINTY(0.1F, 0, 0, 0, 0, 1, 0.21F), // 尖锐
        TINY(0, 0, 0, -0.18F, 0, -1, -0.3276F), // 微小
        TERRIBLE(-0.15F, 0, 0, -0.13F, -0.15F, -2, -0.6049F), // 可怕
        SMALL(0, 0, 0, -0.1F, 0, -1, -0.19F), // 小
        DULL(-0.15F, 0, 0, 0, 0, -1, -0.2775F), // 钝
        UNHAPPY(0, -0.1F, 0, -0.1F, -0.1F, -2, -0.4686F), // 倒霉
        BULKY(0.05F, -0.15F, 0, 0.1F, 0.1F, 1, 0.1662F), // 笨蛋
        SHAMEFUL(-0.1F, 0, 0, 0.1F, -0.2F, -2, -0.3727F), // 可耻
        HEAVY(0, -0.1F, 0, 0, 0.15F, 0, 0.0712F), // 重
        LIGHT(0, 0.15F, 0, 0, -0.1F, 0, 0.0712F), // 轻
        LEGENDARY(0.15F, 0.1F, 0.05F, 0.1F, 0.15F, 2, 2.0985F); // 传奇

        public final float attackDamage;
        public final float attackSpeed;
        public final float criticalChance;
        public final float size;
        public final float knockBack;
        public final int tier;
        public final float value;

        Melee(float attackDamage, float attackSpeed, float criticalChance, float size, float knockBack, int tier, float value) {
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.criticalChance = criticalChance;
            this.size = size;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name().toLowerCase();
            itemPrefix.attackDamage = attackDamage;
            itemPrefix.attackSpeed = attackSpeed;
            itemPrefix.criticalChance = criticalChance;
            itemPrefix.size = size;
            itemPrefix.knockBack = knockBack;
            itemPrefix.tier = tier;
            itemPrefix.value = value;
        }

        @Override
        public boolean isHarmful() {
            return tier < 0 && this != SHAMEFUL;
        }

        public static Melee create(String name, float attackDamage, float attackSpeed, float criticalChance, float size, float knockBack, int tier, float value) {
            throw new IllegalStateException("Enum not extended");
        }
    }

    enum Ranged implements ModPrefix {
        SIGHTED(0.1F, 0, 0.03F, 0, 0, 1, 0.3596F), // 精准
        RAPID(0, 0.15F, 0, 0.1F, 0, 2, 0.6002F), // 迅速
        HASTY(0, 0.1F, 0, 0.15F, 0, 2, 0.6002F), // 急速
        INTIMIDATING(0, 0, 0, 0.05F, 0.15F, 2, 0.4581F), // 恐怖
        DEADLY(0.1F, 0.05F, 0.02F, 0.05F, 0.05F, 2, 0.7538F), // 致命
        STAUNCH(0.1F, 0, 0, 0, 0.15F, 2, 0.6002F), // 可靠
        AWFUL(-0.15F, 0, 0, -0.1F, -0.1F, -2, -0.526F), // 可畏
        LETHARGIC(0, -0.15F, 0, -0.1F, 0, -2, -0.4148F), // 无力
        AWKWARD(0, -0.1F, 0, 0, -0.2F, -2, -0.4816F), // 粗笨
        POWERFUL(0.15F, -0.1F, 0.01F, 0, 0, 1, 0.1145F), // 强大
        FRENZYING(-0.15F, 0.15F, 0, 0, 0, 0, -0.0445F), // 暴怒
        UNREAL(0.15F, 0.1F, 0.05F, 0.1F, 0.15F, 2, 2.0985F); // 虚幻

        public final float attackDamage;
        public final float attackSpeed;
        public final float criticalChance;
        public final float velocity;
        public final float knockBack;
        public final int tier;
        public final float value;

        Ranged(float attackDamage, float attackSpeed, float criticalChance, float velocity, float knockBack, int tier, float value) {
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.criticalChance = criticalChance;
            this.velocity = velocity;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name().toLowerCase();
            itemPrefix.attackDamage = attackDamage;
            itemPrefix.attackSpeed = attackSpeed;
            itemPrefix.criticalChance = criticalChance;
            itemPrefix.velocity = velocity;
            itemPrefix.knockBack = knockBack;
            itemPrefix.tier = tier;
            itemPrefix.value = value;
        }

        @Override
        public boolean isHarmful() {
            return tier < 0 && this != FRENZYING;
        }

        public static Ranged create(String name, float attackDamage, float attackSpeed, float criticalChance, float velocity, float knockBack, int tier, float value) {
            throw new IllegalStateException("Enum not extended");
        }
    }

    enum MagicAndSumming implements ModPrefix {
        MYTHIC(0.1F, 0, 0, -0.15F, 0, 2, 0.6002F), // 神秘
        ADEPT(0, 0, 0, -0.15F, 0, 1, 0.3225F), // 精巧
        MASTERFUL(0.15F, 0, 0, -0.15F, 0.05F, 2, 0.9263F), // 精湛
        INEPT(0, 0, 0, 0.1F, 0, -1, -0.19F), // 笨拙
        IGNORANT(-0.1F, 0, 0, 0.2F, 0, -2, -0.4816F), // 无知
        DERANGED(-0.1F, 0, 0, 0, -0.1F, -1, -0.3439F), // 错乱
        INTENSE(0.1F, 0, 0, 0.15F, 0, -1, -0.1258F), // 威猛
        TABOO(0, 0.1F, 0, 0.1F, 0.1F, 1, 0.1859F), // 禁忌
        CELESTIAL(0.1F, -0.1F, 0, -0.1F, 0.1F, 1, 0.1194F), // 天界
        FURIOUS(0.15F, 0, 0, 0.2F, 0.15F, 1, 0.1194F), // 狂怒
        MANIC(-0.1F, 0.1F, 0, -0.1F, 0, 1, 0.1859F), // 狂躁
        MYTHICAL(0.15F, 0.1F, 0.05F, -0.1F, 0.15F, 2, 2.0985F); // 神话

        public final float attackDamage;
        public final float attackSpeed;
        public final float criticalChance;
        public final float manaCost;
        public final float knockBack;
        public final int tier;
        public final float value;

        MagicAndSumming(float attackDamage, float attackSpeed, float criticalChance, float manaCost, float knockBack, int tier, float value) {
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.criticalChance = criticalChance;
            this.manaCost = manaCost;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name().toLowerCase();
            itemPrefix.attackDamage = attackDamage;
            itemPrefix.attackSpeed = attackSpeed;
            itemPrefix.criticalChance = criticalChance;
            itemPrefix.manaCost = manaCost;
            itemPrefix.knockBack = knockBack;
            itemPrefix.tier = tier;
            itemPrefix.value = value;
        }

        @Override
        public boolean isHarmful() {
            return tier < 0 && this != INTENSE;
        }

        public static MagicAndSumming create(String name, float attackDamage, float attackSpeed, float criticalChance, float manaCost, float knockBack, int tier, float value) {
            throw new IllegalStateException("Enum not extended");
        }
    }
}
