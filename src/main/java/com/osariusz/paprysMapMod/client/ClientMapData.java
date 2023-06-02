package com.osariusz.paprysMapMod.client;

import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ClientMapData {

    private static ClientMapData INSTANCE = new ClientMapData();
    private LogicalMap clientLogicalMap;

    private double xOffset = 0, yOffset = 0;

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

    public double getxOffset() {
        return xOffset;
    }

    public void addxOffset(double x) {
        setxOffset(getxOffset() + x);
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void addyOffset(double y) {
        setyOffset(getyOffset() + y);
    }

    public void setyOffset(double yOffset) {
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
        return getLogicalMap().getWidth();
    }

    public int getMapHeight() {
        return getLogicalMap().getHeight();
    }

    public Vec2 getPlayerPositionOnMap(){
        Vec3 playerPosition = Minecraft.getInstance().player.position();
        int x = (int)(playerPosition.x/clientLogicalMap.getxStep());
        int y = (int)(playerPosition.z/clientLogicalMap.getyStep());
        Vec2 positionOnMap = new Vec2(x,y);
        return positionOnMap;
    }
}
