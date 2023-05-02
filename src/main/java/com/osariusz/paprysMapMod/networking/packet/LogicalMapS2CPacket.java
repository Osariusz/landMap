package com.osariusz.paprysMapMod.networking.packet;


import com.osariusz.paprysMapMod.client.LogicalMapData;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LogicalMapS2CPacket {


    int xAmount, yAmount;
    List<List<Boolean>> isWater;
    Vec3i start;
    double xStep, yStep;

    public LogicalMapS2CPacket(LogicalMap logicalMap){
        xAmount = logicalMap.getIsWater().size();
        if(xAmount < 1){
            throw new IndexOutOfBoundsException();
        }
        yAmount = logicalMap.getIsWater().get(0).size();
        this.isWater = logicalMap.getIsWater();
        this.start = logicalMap.getStart();
        this.xStep = logicalMap.getxStep();
        this.yStep = logicalMap.getyStep();
    }

    public LogicalMapS2CPacket(FriendlyByteBuf buf){
        xAmount = buf.readInt();
        yAmount = buf.readInt();
        isWater = new ArrayList<>();
        for(int i = 0;i<xAmount;i++){
            isWater.add(new ArrayList<>());
            for(int j = 0;j<yAmount;j++){
                isWater.get(i).add(buf.readBoolean());
            }
        }
        start = new Vec3i(buf.readInt(),buf.readInt(),buf.readInt());
        xStep = buf.readDouble();
        yStep = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(xAmount);
        buf.writeInt(yAmount);
        for(int i = 0;i<xAmount;i++){
            for(int j = 0;j<yAmount;j++){
                buf.writeBoolean(isWater.get(i).get(j));
            }
        }
        buf.writeInt(start.getX());
        buf.writeInt(start.getY());
        buf.writeInt(start.getZ());
        buf.writeDouble(xStep);
        buf.writeDouble(yStep);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LogicalMapData.setLogicalMap(new LogicalMap(isWater,start,xStep,yStep));
        });
        return true;
    }
}
