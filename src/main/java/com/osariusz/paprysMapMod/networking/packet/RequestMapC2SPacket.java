package com.osariusz.paprysMapMod.networking.packet;

import com.osariusz.paprysMapMod.server.ServerMapData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class RequestMapC2SPacket {


    public RequestMapC2SPacket(){

    }

    public RequestMapC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerMapData.getInstance().playerMapOpen(player);
        });
        return true;
    }
}
