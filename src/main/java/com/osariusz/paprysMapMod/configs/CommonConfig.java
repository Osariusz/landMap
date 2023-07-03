package com.osariusz.paprysMapMod.configs;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_SEGMENTS_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_SEGMENTS_Y;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_XRADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_YRADIUS;

    static {
        BUILDER.push("Map server configs");

        MAP_SEGMENTS_X = BUILDER.comment("Pixel width of maps").defineInRange("Map Segments X",600,1,Integer.MAX_VALUE);
        MAP_SEGMENTS_Y = BUILDER.comment("Pixel height of maps").defineInRange("Map Segments Y",300,1,Integer.MAX_VALUE);
        MAP_XRADIUS = BUILDER.comment("Block width of a map divided by 2").defineInRange("Map XRadius",30000,1,Integer.MAX_VALUE);
        MAP_YRADIUS = BUILDER.comment("Block height of a map divided by 2").defineInRange("Map YRadius",15000,1,Integer.MAX_VALUE);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
