package com.osariusz.paprysMapMod.server;

import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import com.osariusz.paprysMapMod.menus.MapMenu;
import com.osariusz.paprysMapMod.networking.LogicalMapMessages;
import com.osariusz.paprysMapMod.networking.packet.LogicalMapS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkHooks;

public class ServerMapData {

    private static ServerMapData INSTANCE = new ServerMapData();

    LogicalMap logicalMap;

    public static ServerMapData getInstance(){
        return INSTANCE;
    }

    public void playerMapOpen(ServerPlayer player){
        int width = 1000;
        int height = 500;
        if(true){
            logicalMap = new LogicalMap(player.getLevel(), player.position(),10000,10000,width,height);
        }
        System.out.println("blockclick");
        LogicalMapMessages.sendToPlayer(new LogicalMapS2CPacket(logicalMap),player);
        NetworkHooks.openScreen(player,new SimpleMenuProvider(
                (containerId, playerInventory, playerLambda) -> new MapMenu(containerId,playerInventory), Component.translatable("babel"))
        );
        System.out.println("blockclick2");
    }
}
