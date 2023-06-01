package com.osariusz.paprysMapMod.client;

import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;

public class ClientMapData {

    private static ClientMapData INSTANCE = new ClientMapData();
    private LogicalMap clientLogicalMap;

    private int xOffset = 0, yOffset = 0;

    public void setMapScale(float mapScale) {
        if (mapScale < 0.1f) {
            mapScale = 0.1f;
        } else if (mapScale > 500.0f) {
            mapScale = 500.0f;
        }
        this.mapScale = mapScale;
    }

    public void addMapScale(float offset) {
        setMapScale(getMapScale() + offset);
    }

    public float getMapScale() {
        return mapScale;
    }

    private float mapScale = 1.0f;

    public int getxOffset() {
        return xOffset;
    }

    public void addxOffset(int x) {
        setxOffset(getxOffset() + x);
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void addyOffset(int y) {
        setyOffset(getyOffset() + y);
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }


    public static ClientMapData getInstance() {
        return INSTANCE;
    }

    public void setLogicalMap(LogicalMap logicalMap) {
        clientLogicalMap = logicalMap;
    }

    public LogicalMap getLogicalMap() {
        if (clientLogicalMap == null) {
            clientLogicalMap = new LogicalMap(Minecraft.getInstance().level, new Vec3i(0, 0, 0), 5, 5, 5, 5);
        }
        return clientLogicalMap;
    }

    public int getMapWidth() {
        //return Math.min((int) (Minecraft.getInstance().getWindow().getWidth() / getMapScale()), getLogicalMap().getWidth());
        return (int) (Minecraft.getInstance().getWindow().getWidth());
    }

    public int getMapHeight() {
        int h = (int) (Minecraft.getInstance().getWindow().getHeight() / getMapScale());
        //return Math.min((int) (Minecraft.getInstance().getWindow().getHeight() / getMapScale()), getLogicalMap().getHeight());
        return (int) (Minecraft.getInstance().getWindow().getHeight());
    }
}
