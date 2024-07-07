package org.confluence.mod.block.functional;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public class StateProperties {
    public static final BooleanProperty SIGNAL = BooleanProperty.create("signal"); // 电网信号
    public static final BooleanProperty VISIBLE = BooleanProperty.create("visible");
    public static final BooleanProperty REVERSE = BooleanProperty.create("reverse");
    public static final BooleanProperty DRIVE = BooleanProperty.create("drive");
    public enum SwordType implements StringRepresentable {
        Null("null"),
        EnchantedSword("enchanted_sword"),
        Terragrim("terragrim"),
        RottenSword("rotten_sword");

        private final String name;

        private SwordType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
