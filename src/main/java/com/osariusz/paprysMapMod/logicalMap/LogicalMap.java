package com.osariusz.paprysMapMod.logicalMap;

import com.osariusz.paprysMapMod.configs.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    List<List<Boolean>> isWater = new ArrayList<>();

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

    public double getBlockWidth(){
        return mapSegmentsX*xStep;
    }

    public double getBlockHeight(){
        return mapSegmentsY*yStep;
    }

    public double getyStep() {
        return yStep;
    }

    public List<List<Boolean>> getIsWater() {
        return isWater;
    }

    public Boolean isWater(int x, int y) {
        return isWater.get(x).get(y);
    }


    public Boolean isInside(double x, double y) {
        int centerX = (int) (start.getX()+getBlockWidth()/2);
        int centerY = (int) (start.getZ()+getBlockHeight()/2);
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
                    int logicalY = (int) (y * yStep + startY);
                    return biomeManager.getBiome(new BlockPos(logicalX, level.getSeaLevel(), logicalY));
                });
    }

    public LogicalMap(Level level, Vec3i centre, int radiusX, int radiusY, int mapSegmentsX, int mapSegmentsY) {
        if (level != null) {
            this.mapSegmentsX = mapSegmentsX;
            this.mapSegmentsY = mapSegmentsY;
            xStep = (radiusX * 2) / (double) mapSegmentsX;
            yStep = (radiusY * 2) / (double) mapSegmentsY;

            start = centre.offset(-radiusX, 0, -radiusY);

            List<List<Holder<Biome>>> biomes = new ArrayList<>();

            for (int x = 0; x < mapSegmentsX; x++) {
                int logicalX = (int) (x * xStep + start.getX());
                biomes.add(getBiomesOnX(level, logicalX, start.getZ(), radiusY).collect(Collectors.toList()));
            }


            this.isWater = biomesToWater(biomes);
        }
    }

    public LogicalMap(Level level, Vec3 centre, int radiusX, int radiusY, int mapSegmentsX, int mapSegmentsY) {
        this(level, new Vec3i(centre.x, centre.y, centre.z), radiusX, radiusY, mapSegmentsX, mapSegmentsY);
    }

    public LogicalMap(@NotNull List<List<Boolean>> isWater, @NotNull Vec3i start, double xStep, double yStep, int mapSegmentsX, int mapSegmentsY) {
        this.isWater = isWater;
        this.start = start;
        this.xStep = xStep;
        this.yStep = yStep;
        this.mapSegmentsY = mapSegmentsY;
        this.mapSegmentsX = mapSegmentsX;
    }


}
