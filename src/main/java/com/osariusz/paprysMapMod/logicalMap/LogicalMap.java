package com.osariusz.paprysMapMod.logicalMap;

import com.ibm.icu.impl.Pair;
import com.osariusz.paprysMapMod.client.LogicalMapData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class LogicalMap implements Serializable {

    static final int mapSegmentsX = 450;
    static final int mapSegmentsY = 251;

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

    public double getyStep() {
        return yStep;
    }

    public List<List<Boolean>> getIsWater() {
        return isWater;
    }


    public List<List<Boolean>> biomesToWater(List<List<Holder<Biome>>> biomes){
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
        List<List<Boolean>> isWater = new ArrayList<>();
        for(int x = 0;x<biomes.size();++x){
            isWater.add(new ArrayList<>());
            for(int y = 0;y<biomes.get(x).size();++y){
                boolean waterBiome = false;
                for(ResourceKey<Biome> biome : waterBiomes){
                    if(biomes.get(x).get(y).is(biome)){
                        waterBiome = true;
                    }
                }
                if(waterBiome){
                    isWater.get(x).add(true);
                }
                else{
                    isWater.get(x).add(false);
                }
            }
        }
        return isWater;
    }

    public LogicalMap(Level level, Vec3i centre, int radiusX, int radiusY){
        if(level != null){
            xStep = (radiusX*2)/(double)mapSegmentsX;
            yStep = (radiusY*2)/(double)mapSegmentsY;

            start = centre.offset(-radiusX,0,-radiusY);

            List<List<Holder<Biome>>> biomes = new ArrayList<>();

            for(int x = 0;x<mapSegmentsX;x++){
                int logicalX = (int)(x*xStep+centre.getX()-radiusX);
                biomes.add(new ArrayList<>());
                for(int y = 0;y<mapSegmentsY;y++){
                    int logicalY = (int) (y*yStep+centre.getZ()-radiusY);
                    biomes.get(x).add(level.getBiome(new BlockPos(logicalX,level.getSeaLevel(),logicalY)));
                }
            }

            this.isWater = biomesToWater(biomes);
        }
    }

    public LogicalMap(@NotNull List<List<Boolean>> isWater, @NotNull Vec3i start, double xStep, double yStep){
        this.isWater = isWater;
        this.start = start;
        this.xStep = xStep;
        this.yStep = yStep;
    }

    public ArrayList<LogicalLine> getLines(){
        ArrayList<LogicalLine> result = new ArrayList<>();
        int c = 0;
        for(int x = 0;x<isWater.size();++x){
            for(int y = 0;y<isWater.get(x).size();++y){
                System.out.println("Rysuje linie"+c++);
                if(x == mapSegmentsX/2 && y == mapSegmentsY/2){
                    result.add(new LogicalLine(x, x, y, -2000));
                    continue;
                }
                Boolean waterTile = isWater.get(x).get(y);
                if(!waterTile){
                    ArrayList<Pair<Integer,Integer>> positionsToCheck = new ArrayList<>(List.of(
                            Pair.of(x-1,y),
                            Pair.of(x,y+1),
                            Pair.of(x+1,y),
                            Pair.of(x,y-1)
                    ));
                    boolean waterNeighborFound = false;
                    /*for(Pair<Integer,Integer> pair : positionsToCheck){
                        try{
                            Boolean waterNeighbor = isWater.get(pair.first).get(pair.second);
                            if(waterNeighbor){
                                waterNeighborFound = true;
                                break;
                            }
                        } catch (Exception e){
                            waterNeighborFound = true;
                        }
                    }*/
                    waterNeighborFound = true;
                    if(waterNeighborFound){
                        result.add(new LogicalLine(x, x, y, isWater.get(x).get(y).equals(true) ? -2 : -12574688));
                    }
                }
            }
        }
        return result;
    }




}
