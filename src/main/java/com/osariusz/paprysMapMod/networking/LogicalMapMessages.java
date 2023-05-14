package com.osariusz.paprysMapMod.networking;


import com.osariusz.paprysMapMod.PapyrusMapMod;
import com.osariusz.paprysMapMod.networking.packet.LogicalMapS2CPacket;
import com.osariusz.paprysMapMod.networking.packet.RequestMapC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class LogicalMapMessages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PapyrusMapMod.MODID, "mesages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(LogicalMapS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LogicalMapS2CPacket::new)
                .encoder(LogicalMapS2CPacket::toBytes)
                .consumerMainThread(LogicalMapS2CPacket::handle)
                .add();

        net.messageBuilder(RequestMapC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestMapC2SPacket::new)
                .encoder(RequestMapC2SPacket::toBytes)
                .consumerMainThread(RequestMapC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

}
