package com.osariusz.landmap.networking.packet;

import com.osariusz.landmap.server.ServerMapData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class RequestMapC2SPacket {


    public RequestMapC2SPacket() {

    }

    public RequestMapC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Thread thread = new Thread(
                    () -> {
                        try {
                            assert player != null;
                            ServerMapData.getInstance().playerMapOpen(player);
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            thread.start();
        });
        return true;
    }
}
