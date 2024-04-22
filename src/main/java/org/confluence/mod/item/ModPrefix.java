package org.confluence.mod.item;

public final class ModPrefix {
    public enum Curio {
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
        QUICK(0, 0, 0, 0, 0.04, 0, 2, 0.44), // 迅捷

        ARCANE(0, 0, 0, 0, 0, 20, 1, 0.3225); // 奥秘

        public final int armor;
        public final double criticalChance;
        public final double attackDamage;
        public final double attackSpeed;
        public final double movementSpeed;
        public final int mana;
        public final int level;
        public final double value;

        Curio(int armor, double criticalChance, double attackDamage, double attackSpeed, double movementSpeed, int mana, int level, double value) {
            this.armor = armor;
            this.criticalChance = criticalChance;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.movementSpeed = movementSpeed;
            this.mana = mana;
            this.level = level;
            this.value = value;
        }
    }

    public enum Universal {

    }

    public enum Common {

    }

    public enum Melee {

    }

    public enum Ranged {

    }

    public enum MagicAndSummoning {

    }
}
