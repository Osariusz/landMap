package com.osariusz.paprysMapMod.client;

import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import net.minecraft.client.Minecraft;

public class ClientMapData {
    private static LogicalMap clientLogicalMap;

    public static void setLogicalMap(LogicalMap logicalMap){
        clientLogicalMap = logicalMap;
    }
    public static LogicalMap getLogicalMap(){
        return clientLogicalMap;
    }

    public static int getMapWidth(){
        int t = Minecraft.getInstance().getWindow().getWidth();
        return Math.min(Minecraft.getInstance().getWindow().getWidth(), clientLogicalMap.getWidth());
    }
    public static int getMapHeight(){
        int t = Minecraft.getInstance().getWindow().getHeight();
        return Math.min(Minecraft.getInstance().getWindow().getHeight(), clientLogicalMap.getHeight());
    }
}
