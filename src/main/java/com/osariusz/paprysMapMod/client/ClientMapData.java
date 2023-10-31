package com.osariusz.paprysMapMod.client;

import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.awt.geom.Point2D;

public class ClientMapData {

    private final static ClientMapData INSTANCE = new ClientMapData();
    private LogicalMap clientLogicalMap;

    @Getter
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

    @Getter
    private float mapScale = 1.0f;

    public void addxOffset(double x) {
        setxOffset(getXOffset() + x);
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public void addyOffset(double y) {
        setyOffset(getYOffset() + y);
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
            clientLogicalMap = LogicalMap.builder()
                    .center(new Vec3i(0, 0, 0))
                    .radiusX(5)
                    .radiusY(5)
                    .mapSegmentsX(5)
                    .mapSegmentsY(5)
                    .build()
                    .generateIsWater(Minecraft.getInstance().level);
        }
        return clientLogicalMap;
    }

    public int getMapWidth() {
        return getLogicalMap().getWidth();
    }

    public int getMapHeight() {
        return getLogicalMap().getHeight();
    }

    public Point2D.Double getPlayerPositionOnMap() {
        Vec3 playerPosition = Minecraft.getInstance().player.position();
        double x = ((playerPosition.x - clientLogicalMap.getCenter().getX()) / clientLogicalMap.getXStep());
        double y = ((playerPosition.z - clientLogicalMap.getCenter().getZ()) / clientLogicalMap.getYStep());
        Point2D.Double positionOnMap = new Point2D.Double(x, y);
        return positionOnMap;
    }

    public Point mapToStepCoordinates(Point2D.Double mapCoordinates) {
        Point2D.Double playerPosition = getPlayerPositionOnMap();
        double x = mapCoordinates.x;// / getMapScale();
        double y = mapCoordinates.y;// / getMapScale();
        x = x + ClientMapData.getInstance().getXOffset() - INSTANCE.getMapWidth() / 2.0;
        y = y + ClientMapData.getInstance().getYOffset() - INSTANCE.getMapHeight() / 2.0;
        int stepX = (int) Math.floor(x) + (int) playerPosition.x; //a floor is needed to handle negative coordinates properly
        int stepY = (int) Math.floor(y) + (int) playerPosition.y;
        return new Point(stepX, stepY);
    }

    public Point2D.Double mapToCentreBlockCoordinates(Point2D.Double mapCoordinates) {
        Point blockCoordinates = mapToStepCoordinates(mapCoordinates);
        int blockX = blockCoordinates.x; //truncate the decimal values
        int blockY = blockCoordinates.y;
        blockX = (int) ((double) blockX * getLogicalMap().getXStep());
        blockY = (int) ((double) blockY * getLogicalMap().getYStep());
        Vec3i start = INSTANCE.getLogicalMap().getStart();
        blockX += start.getX() + clientLogicalMap.getBlockWidth() / 2;
        blockY += start.getZ() + clientLogicalMap.getBlockHeight() / 2;
        blockX += Math.signum(blockCoordinates.x) * ClientMapData.getInstance().getLogicalMap().getXStep() / 2;
        blockY += Math.signum(blockCoordinates.y) * ClientMapData.getInstance().getLogicalMap().getYStep() / 2;
        return new Point2D.Double(blockX, blockY);
    }

}
