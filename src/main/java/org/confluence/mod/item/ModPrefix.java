package org.confluence.mod.item;

import org.confluence.mod.capability.prefix.ItemPrefix;

public interface ModPrefix {
    void copyTo(ItemPrefix itemPrefix);

    boolean isHarmful();

    enum Curio implements ModPrefix {
        HARD(1, 0, 0, 0, 0, 0, 1, 0.1025), // 坚硬
        GUARDING(2, 0, 0, 0, 0, 0, 1, 0.21), // 守护
        ARMORED(3, 0, 0, 0, 0, 0, 1, 0.3225), // 装甲
        WARDING(4, 0, 0, 0, 0, 0, 2, 0.44), // 护佑

        PRECISE(0, 0.02, 0, 0, 0, 0, 1, 0.21), // 精确
        LUCKY(0, 0.04, 0, 0, 0, 0, 2, 0.44), // 幸运

        JAGGED(0, 0, 0.01, 0, 0, 0, 1, 0.1025), // 锯齿
        SPIKED(0, 0, 0.02, 0, 0, 0, 1, 0.21), // 尖刺
        ANGRY(0, 0, 0.03, 0, 0, 0, 1, 0.3225), // 愤怒
        MENACING(0, 0, 0.04, 0, 0, 0, 2, 0.44), // 险恶

        WILD(0, 0, 0, 0.01, 0, 0, 1, 0.1025), // 狂野
        RASH(0, 0, 0, 0.02, 0, 0, 1, 0.21), // 鲁莽
        INTREPID(0, 0, 0, 0.03, 0, 0, 1, 0.3225), // 勇猛
        VIOLENT(0, 0, 0, 0.04, 0, 0, 2, 0.44), // 暴力

        BRISK(0, 0, 0, 0, 0.01, 0, 1, 0.1025), // 轻快
        FLEETING(0, 0, 0, 0, 0.02, 0, 1, 0.21), // 快速
        HASTY(0, 0, 0, 0, 0.03, 0, 1, 0.3225), // 急速
        CURIO_QUICK(0, 0, 0, 0, 0.04, 0, 2, 0.44), // 迅捷

        ARCANE(0, 0, 0, 0, 0, 20, 1, 0.3225); // 奥秘

        public final int armor;
        public final double criticalChance;
        public final double attackDamage;
        public final double attackSpeed;
        public final double movementSpeed;
        public final int additionalMana;
        public final int tier;
        public final double value;

        Curio(int armor, double criticalChance, double attackDamage, double attackSpeed, double movementSpeed, int additionalMana, int tier, double value) {
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
            itemPrefix.name = name();
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
    }

    enum Universal implements ModPrefix {
        KEEN(0, 0.03, 0, 1, 0.1236), // 锐利
        SUPERIOR(0.1, 0.03, 0.1, 2, 0.6451), // 高端
        FORCEFUL(0, 0, 0.15, 1, 0.3225), // 强力
        BROKEN(-0.3, 0, -0.2, -2, -0.6864), // 碎裂
        DAMAGED(-0.15, 0, 0, -1, -0.2775), // 破损
        SHODDY(-0.1, 0, -0.15, -2, -0.4148), // 粗劣
        HURTFUL(0.1, 0, 0, 1, 0.21), // 致伤
        STRONG(0, 0, 0.15, 1, 0.21), // 强劲
        UNPLEASANT(0.05, 0, 0.15, 2, 0.4581), // 粗鲁
        WEAK(0, 0, -0.2, -2, -0.36), // 虚弱
        RUTHLESS(0.18, 0, -0.1, 1, 0.1278), // 无情
        GODLY(0.15, 0.05, 0.15, 2, 1.1163), // 神级
        DEMONIC(0.15, 0.05, 0, 2, 0.6002), // 恶魔
        ZEALOUS(0, 0.05, 0, 1, 0.21); // 狂热

        public final double attackDamage;
        public final double criticalChance;
        public final double knockBack;
        public final int tier;
        public final double value;

        Universal(double attackDamage, double criticalChance, double knockBack, int tier, double value) {
            this.attackDamage = attackDamage;
            this.criticalChance = criticalChance;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name();
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
    }

    enum Common implements ModPrefix {
        COMMON_QUICK(0, 0.1, 0, 0, 1, 0.21), // 迅捷
        COMMON_DEADLY(0.1, 0.1, 0, 0, 2, 0.4641), // 致命
        AGILE(0, 0.1, 0.03, 0, 1, 0.3596), // 灵活
        NIMBLE(0, 0.05, 0, 0, 1, 0.1025), // 灵巧
        MURDEROUS(0.07, 0.06, 0.03, 0, 2, 0.4454), // 残暴
        SLOW(0, -0.15, 0, 0, -1, -0.2775), // 缓慢
        SLUGGISH(0, -0.2, 0, 0, -2, -0.36), // 迟钝
        LAZY(0, -0.08, 0, 0, -1, -0.1536), // 呆滞
        ANNOYING(-0.2, -0.15, 0, 0, -2, -0.5376), // 惹恼
        NASTY(0.05, 0.1, 0.02, -0.1, 1, 0.1687); // 凶险

        public final double attackDamage;
        public final double attackSpeed;
        public final double criticalChance;
        public final double knockBack;
        public final int tier;
        public final double value;

        Common(double attackDamage, double attackSpeed, double criticalChance, double knockBack, int tier, double value) {
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.criticalChance = criticalChance;
            this.knockBack = knockBack;
            this.tier = tier;
            this.value = value;
        }

        @Override
        public void copyTo(ItemPrefix itemPrefix) {
            itemPrefix.name = name();
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
    }

    enum Melee implements ModPrefix {
        LARGE(0, 0, 0, 0.12, 0, 1, 0.2544), // 大
        MASSIVE(0, 0, 0, 0.18, 0, 1, 0.3924), // 巨大
        DANGEROUS(0.05, 0, 0.02, 0.05, 0, 1, 0.3147), // 危险
        SAVAGE(0.1, 0, 0, 0.1, 0.1, 2, 0.7716), // 凶险
        SHARP(0.15, 0, 0, 0, 0, 1, 0.3225), // 锋利
        POINTY(0.1, 0, 0, 0, 0, 1, 0.21), // 尖锐
        TINY(0, 0, 0, -0.18, 0, -1, -0.3276), // 微小
        TERRIBLE(-0.15, 0, 0, -0.13, -0.15, -2, -0.6049), // 可怕
        SMALL(0, 0, 0, -0.1, 0, -1, -0.19), // 小
        DULL(-0.15, 0, 0, 0, 0, -1, -0.2775), // 钝
        UNHAPPY(0, -0.1, 0, -0.1, -0.1, -2, -0.4686), // 倒霉
        BULKY(0.05, -0.15, 0, 0.1, 0.1, 1, 0.1662), // 笨蛋
        SHAMEFUL(-0.1, 0, 0, 0.1, -0.2, -2, -0.3727), // 可耻
        HEAVY(0, -0.1, 0, 0, 0.15, 0, 0.0712), // 重
        LIGHT(0, 0.15, 0, 0, -0.1, 0, 0.0712), // 轻
        LEGENDARY(0.15, 0.1, 0.05, 0.1, 0.15, 2, 2.0985); // 传奇

        public final double attackDamage;
        public final double attackSpeed;
        public final double criticalChance;
        public final double size;
        public final double knockBack;
        public final int tier;
        public final double value;

        Melee(double attackDamage, double attackSpeed, double criticalChance, double size, double knockBack, int tier, double value) {
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
            itemPrefix.name = name();
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
    }

    enum Ranged implements ModPrefix {
        SIGHTED(0.1, 0, 0.03, 0, 0, 1, 0.3596), // 精准
        RAPID(0, 0.15, 0, 0.1, 0, 2, 0.6002), // 迅速
        HASTY(0, 0.1, 0, 0.15, 0, 2, 0.6002), // 急速
        INTIMIDATING(0, 0, 0, 0.05, 0.15, 2, 0.4581), // 恐怖
        RANGED_DEADLY(0.1, 0.05, 0.02, 0.05, 0.05, 2, 0.7538), // 致命
        STAUNCH(0.1, 0, 0, 0, 0.15, 2, 0.6002), // 可靠
        AWFUL(-0.15, 0, 0, -0.1, -0.1, -2, -0.526), // 可畏
        LETHARGIC(0, -0.15, 0, -0.1, 0, -2, -0.4148), // 无力
        AWKWARD(0, -0.1, 0, 0, -0.2, -2, -0.4816), // 粗笨
        POWERFUL(0.15, -0.1, 0.01, 0, 0, 1, 0.1145), // 强大
        FRENZYING(-0.15, 0.15, 0, 0, 0, 0, -0.0445), // 暴怒
        UNREAL(0.15, 0.1, 0.05, 0.1, 0.15, 2, 2.0985); // 虚幻

        public final double attackDamage;
        public final double attackSpeed;
        public final double criticalChance;
        public final double velocity;
        public final double knockBack;
        public final int tier;
        public final double value;

        Ranged(double attackDamage, double attackSpeed, double criticalChance, double velocity, double knockBack, int tier, double value) {
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
            itemPrefix.name = name();
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
    }

    enum MagicAndSumming implements ModPrefix {
        MYTHIC(0.1, 0, 0, -0.15, 0, 2, 0.6002), // 神秘
        ADEPT(0, 0, 0, -0.15, 0, 1, 0.3225), // 精巧
        MASTERFUL(0.15, 0, 0, -0.15, 0.05, 2, 0.9263), // 精湛
        INEPT(0, 0, 0, 0.1, 0, -1, -0.19), // 笨拙
        IGNORANT(-0.1, 0, 0, 0.2, 0, -2, -0.4816), // 无知
        DERANGED(-0.1, 0, 0, 0, -0.1, -1, -0.3439), // 错乱
        INTENSE(0.1, 0, 0, 0.15, 0, -1, -0.1258), // 威猛
        TABOO(0, 0.1, 0, 0.1, 0.1, 1, 0.1859), // 禁忌
        CELESTIAL(0.1, -0.1, 0, -0.1, 0.1, 1, 0.1194), // 天界
        FURIOUS(0.15, 0, 0, 0.2, 0.15, 1, 0.1194), // 狂怒
        MANIC(-0.1, 0.1, 0, -0.1, 0, 1, 0.1859), // 狂躁
        MYTHICAL(0.15, 0.1, 0.05, -0.1, 0.15, 2, 2.0985); // 神话

        public final double attackDamage;
        public final double attackSpeed;
        public final double criticalChance;
        public final double manaCost;
        public final double knockBack;
        public final int tier;
        public final double value;

        MagicAndSumming(double attackDamage, double attackSpeed, double criticalChance, double manaCost, double knockBack, int tier, double value) {
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
            itemPrefix.name = name();
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
    }
}
