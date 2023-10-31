package com.osariusz.paprysMapMod.networking.packet;


import com.osariusz.paprysMapMod.client.ClientMapData;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LogicalMapS2CPacket {


    int xAmount, yAmount;
    int radiusX, radiusY;
    int mapSegmentsX, mapSegmentsY;
    Vec3i centre;
    List<List<Boolean>> isWater = new ArrayList<>();

    public LogicalMapS2CPacket(LogicalMap logicalMap) {
        xAmount = logicalMap.getIsWater().size();
        if (xAmount < 1) {
            throw new IndexOutOfBoundsException();
        }
        yAmount = logicalMap.getIsWater().get(0).size();
        this.isWater = logicalMap.getIsWater();
        this.centre = logicalMap.getCenter();
        this.radiusX = logicalMap.getRadiusX();
        this.radiusY = logicalMap.getRadiusY();
        this.mapSegmentsX = logicalMap.getMapSegmentsX();
        this.mapSegmentsY = logicalMap.getMapSegmentsY();
    }

    public LogicalMapS2CPacket(FriendlyByteBuf buf) {
        xAmount = buf.readInt();
        yAmount = buf.readInt();
        isWater = new ArrayList<>();
        for (int i = 0; i < xAmount; i++) {
            isWater.add(new ArrayList<>());
            for (int j = 0; j < yAmount; j++) {
                isWater.get(i).add(buf.readBoolean());
            }
        }
        centre = new Vec3i(buf.readInt(), buf.readInt(), buf.readInt());
        radiusX = buf.readInt();
        radiusY = buf.readInt();
        mapSegmentsX = buf.readInt();
        mapSegmentsY = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(xAmount);
        buf.writeInt(yAmount);
        for (int i = 0; i < xAmount; i++) {
            for (int j = 0; j < yAmount; j++) {
                buf.writeBoolean(isWater.get(i).get(j));
            }
        }
        buf.writeInt(centre.getX());
        buf.writeInt(centre.getY());
        buf.writeInt(centre.getZ());
        buf.writeInt(radiusX);
        buf.writeInt(radiusY);
        buf.writeInt(mapSegmentsX);
        buf.writeInt(mapSegmentsY);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientMapData.getInstance().setLogicalMap(
                    LogicalMap.builder()
                            .isWater(isWater)
                            .center(centre)
                            .radiusX(radiusX)
                            .radiusY(radiusY)
                            .mapSegmentsX(mapSegmentsX)
                            .mapSegmentsY(mapSegmentsY)
                    .build()
            );
        });
        return true;
    }
}
