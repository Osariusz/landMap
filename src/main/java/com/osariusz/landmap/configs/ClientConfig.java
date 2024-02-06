package com.osariusz.landmap.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> LAND_COLOR_R;
    public static final ForgeConfigSpec.ConfigValue<Integer> LAND_COLOR_G;
    public static final ForgeConfigSpec.ConfigValue<Integer> LAND_COLOR_B;

    public static final ForgeConfigSpec.ConfigValue<Integer> WATER_COLOR_R;
    public static final ForgeConfigSpec.ConfigValue<Integer> WATER_COLOR_G;
    public static final ForgeConfigSpec.ConfigValue<Integer> WATER_COLOR_B;

    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_COLOR_R;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_COLOR_G;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_COLOR_B;

    static {
        BUILDER.push("Map client configs");
        LAND_COLOR_R = BUILDER.comment("Land color red value").defineInRange("Land R", 75, 0, 255);
        LAND_COLOR_G = BUILDER.comment("Land color green value").defineInRange("Land G", 25, 0, 255);
        LAND_COLOR_B = BUILDER.comment("Land color blue value").defineInRange("Land B", 25, 0, 255);

        WATER_COLOR_R = BUILDER.comment("Water color red value").defineInRange("Water R", 52, 0, 255);
        WATER_COLOR_G = BUILDER.comment("Water color green value").defineInRange("Water G", 166, 0, 255);
        WATER_COLOR_B = BUILDER.comment("Water color blue value").defineInRange("Water B", 218, 0, 255);

        PLAYER_COLOR_R = BUILDER.comment("Player color red value").defineInRange("Player R", 250, 0, 255);
        PLAYER_COLOR_G = BUILDER.comment("Player color green value").defineInRange("Player G", 255, 0, 255);
        PLAYER_COLOR_B = BUILDER.comment("Player color blue value").defineInRange("Player B", 10, 0, 255);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
