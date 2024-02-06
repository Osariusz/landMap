package com.osariusz.landmap.logicalMap;

import com.osariusz.landmap.configs.CommonConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@ToString
public class LogicalMap implements Serializable {

    int radiusX, radiusY;
    int mapSegmentsX, mapSegmentsY;
    Vec3i center;
    List<List<Boolean>> isWater = new ArrayList<>();

    public Vec3i getStart() {
        return center.offset(-radiusX, 0, -radiusY);
    }

    public double getXStep() {
        return (radiusX * 2) / (double) mapSegmentsX;
    }

    public double getYStep() {
        return (radiusY * 2) / (double) mapSegmentsY;
    }

    public int getWidth() {
        return mapSegmentsX;
    }

    public int getHeight() {
        return mapSegmentsY;
    }

    public double getBlockWidth() {
        return mapSegmentsX * getXStep();
    }

    public double getBlockHeight() {
        return mapSegmentsY * getYStep();
    }

    public Boolean isWater(int x, int y) {
        return isWater.get(x).get(y);
    }


    public Boolean isInside(double x, double y) {
        int centerX = getCenter().getX();
        int centerY = getCenter().getZ();
        return Math.abs(x - centerX) <= getBlockWidth() / 2 && Math.abs(y - centerY) <= getBlockHeight() / 2;
    }

    public List<List<Boolean>> biomesToWater(List<List<Holder<Biome>>> biomes) {
        List<String> waterBiomes = CommonConfig.WATER_BIOMES.get();
        List<List<Boolean>> isWater = new ArrayList<>();
        for (int x = 0; x < biomes.size(); ++x) {
            isWater.add(new ArrayList<>());
            for (int y = 0; y < biomes.get(x).size(); ++y) {
                boolean waterBiome = false;
                for (String biome : waterBiomes) {
                    if (biomes.get(x).get(y).unwrapKey().get().location().toString().equals(biome)) {
                        waterBiome = true;
                    }
                }
                if (waterBiome) {
                    isWater.get(x).add(true);
                } else {
                    isWater.get(x).add(false);
                }
            }
        }
        return isWater;
    }

    public Stream<Holder<Biome>> getBiomesOnX(Level level, int logicalX, int startY, int radiusY) {
        BiomeManager biomeManager = level.getBiomeManager();
        return Stream.iterate(0, y -> y + 1).limit(mapSegmentsY).map(
                (y) -> {
                    int logicalY = (int) (y * getYStep() + startY);
                    return biomeManager.getBiome(new BlockPos(logicalX, level.getSeaLevel(), logicalY));
                });
    }

    public LogicalMap generateIsWater(Level level) {
        if (level != null) {
            List<List<Holder<Biome>>> biomes = new ArrayList<>();

            for (int x = 0; x < mapSegmentsX; x++) {
                int logicalX = (int) (x * getXStep() + getStart().getX());
                biomes.add(getBiomesOnX(level, logicalX, getStart().getZ(), radiusY).collect(Collectors.toList()));
            }

            this.isWater = biomesToWater(biomes);
        }
        return this;
    }

}
