package com.osariusz.paprysMapMod.configs;

import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_SEGMENTS_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_SEGMENTS_Y;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_XRADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAP_YRADIUS;

    public static final ForgeConfigSpec.ConfigValue<List<String>> WATER_BIOMES;

    static {
        BUILDER.push("Map server configs");
        MAP_SEGMENTS_X = BUILDER.comment("Pixel width of maps").defineInRange("Map Segments X", 600, 1, Integer.MAX_VALUE);
        MAP_SEGMENTS_Y = BUILDER.comment("Pixel height of maps").defineInRange("Map Segments Y", 300, 1, Integer.MAX_VALUE);
        MAP_XRADIUS = BUILDER.comment("Block width of a map divided by 2").defineInRange("Map XRadius", 30000, 1, Integer.MAX_VALUE);
        MAP_YRADIUS = BUILDER.comment("Block height of a map divided by 2").defineInRange("Map YRadius", 15000, 1, Integer.MAX_VALUE);


        ArrayList biomes = new ArrayList<String>() {
            {
                add(Biomes.OCEAN.location().toString());
                add(Biomes.DEEP_COLD_OCEAN.location().toString());
                add(Biomes.DEEP_OCEAN.location().toString());
                add(Biomes.DEEP_FROZEN_OCEAN.location().toString());
                add(Biomes.DEEP_LUKEWARM_OCEAN.location().toString());
                add(Biomes.COLD_OCEAN.location().toString());
                add(Biomes.FROZEN_OCEAN.location().toString());
                add(Biomes.LUKEWARM_OCEAN.location().toString());
                add(Biomes.WARM_OCEAN.location().toString());
                add(Biomes.FROZEN_RIVER.location().toString());
            }
        };
        WATER_BIOMES = BUILDER.comment("Biomes mapped as water").defineList("Water Biomes", biomes,i -> (true));

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
