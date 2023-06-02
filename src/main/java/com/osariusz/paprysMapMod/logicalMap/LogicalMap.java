package com.osariusz.paprysMapMod.logicalMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogicalMap implements Serializable {

    public int getMapSegmentsX() {
        return mapSegmentsX;
    }

    public int getMapSegmentsY() {
        return mapSegmentsY;
    }

    int mapSegmentsX;
    int mapSegmentsY;

    Vec3i start;
    double xStep;

    double yStep;
    Boolean[][] isWater;

    public Vec3i getStart() {
        return start;
    }

    public double getxStep() {
        return xStep;
    }

    public int getWidth() {
        return mapSegmentsX;
    }

    public int getHeight() {
        return mapSegmentsY;
    }

    public double getyStep() {
        return yStep;
    }

    public Boolean[][] getIsWater() {
        return isWater;
    }

    public Boolean isWater(int x, int y) {
        return isWater[x][y];
    }


    public Boolean[][] biomesToWater(Holder<Biome>[][] biomes) {
        List<ResourceKey<Biome>> waterBiomes = new ArrayList<>(List.of(
                Biomes.OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN,
                Biomes.RIVER,
                Biomes.FROZEN_RIVER
        ));
        Boolean[][] isWater = new Boolean[mapSegmentsX][mapSegmentsY];
        for (int x = 0; x < mapSegmentsX; ++x) {
            for (int y = 0; y < mapSegmentsY; ++y) {
                boolean waterBiome = false;
                for (ResourceKey<Biome> biome : waterBiomes) {
                    if (biomes[x][y].is(biome)) {
                        waterBiome = true;
                    }
                }
                if (waterBiome) {
                    isWater[x][y] = true;
                } else {
                    isWater[x][y] = false;
                }
            }
        }

        return isWater;
    }

    public LogicalMap(Level level, Vec3i centre, int radiusX, int radiusY, int mapSegmentsX, int mapSegmentsY) {
        if (level != null) {
            long s = System.currentTimeMillis();
            this.mapSegmentsX = mapSegmentsX;
            this.mapSegmentsY = mapSegmentsY;
            xStep = (radiusX * 2) / (double) mapSegmentsX;
            yStep = (radiusY * 2) / (double) mapSegmentsY;

            start = centre.offset(-radiusX, 0, -radiusY);

            Holder<Biome>[][] biomes = new Holder[mapSegmentsX][mapSegmentsY];
            //List<List<Holder<Biome>>> biomes = new ArrayList<>();

            for (int x = 0; x < mapSegmentsX; x++) {
                int logicalX = (int) (x * xStep + centre.getX() - radiusX);
                for (int y = 0; y < mapSegmentsY; y++) {
                    int logicalY = (int) (y * yStep + centre.getZ() - radiusY);
                    biomes[x][y] = level.getBiome(new BlockPos(logicalX, level.getSeaLevel(), logicalY));// .get(x).add(level.getBiome(new BlockPos(logicalX, level.getSeaLevel(), logicalY)));
                }
            }

            this.isWater = biomesToWater(biomes);
            System.out.println("Czas:");
            System.out.println(System.currentTimeMillis()-s);
        }
    }

    public LogicalMap(Level level, Vec3 centre, int radiusX, int radiusY, int mapSegmentsX, int mapSegmentsY) {
        this(level, new Vec3i(centre.x, centre.y, centre.z), radiusX, radiusY, mapSegmentsX, mapSegmentsY);
    }

    public LogicalMap(@NotNull Boolean[][] isWater, @NotNull Vec3i start, double xStep, double yStep, int mapSegmentsX, int mapSegmentsY) {
        this.isWater = isWater;
        this.start = start;
        this.xStep = xStep;
        this.yStep = yStep;
        this.mapSegmentsY = mapSegmentsY;
        this.mapSegmentsX = mapSegmentsX;
    }


}
