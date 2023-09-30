package com.osariusz.paprysMapMod.server;

import com.osariusz.paprysMapMod.configs.CommonConfig;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import com.osariusz.paprysMapMod.menus.MapMenu;
import com.osariusz.paprysMapMod.networking.LogicalMapMessages;
import com.osariusz.paprysMapMod.networking.packet.LogicalMapS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

public class ServerMapData {

    private static ServerMapData INSTANCE = new ServerMapData();

    List<LogicalMap> logicalMaps = new ArrayList<>();

    int logicalMapWidth = CommonConfig.MAP_SEGMENTS_X.get();
    int logicalMapHeight = CommonConfig.MAP_SEGMENTS_Y.get();
    int xMapRadius = CommonConfig.MAP_XRADIUS.get();
    int yMapRadius = CommonConfig.MAP_YRADIUS.get();

    public static ServerMapData getInstance() {
        return INSTANCE;
    }

    public LogicalMap getLogicalMap(ServerLevel level, Vec3 playerPosition){
        for(LogicalMap logicalMap : logicalMaps){
            if(logicalMap.isInside(playerPosition.x,playerPosition.z)){
                return logicalMap;
            }
        }
        prepareLogicalMap(level, playerPosition);
        return logicalMaps.get(logicalMaps.size()-1);
    }

    public void prepareLogicalMap(ServerLevel level, Vec3 playerPosition){
        int centreX = (int)(Math.round(playerPosition.x/(2*xMapRadius))*2*xMapRadius);
        int centreY = (int)(Math.round(playerPosition.z/(2*yMapRadius))*2*yMapRadius);
        logicalMaps.add(new LogicalMap(level,new Vec3(centreX,0,centreY), xMapRadius, yMapRadius, logicalMapWidth, logicalMapHeight));
    }

    public void playerMapOpen(ServerPlayer player) {
        LogicalMapMessages.sendToPlayer(new LogicalMapS2CPacket(getLogicalMap(player.getLevel(), player.position())), player);
        NetworkHooks.openScreen(player, new SimpleMenuProvider(
                (containerId, playerInventory, playerLambda) -> new MapMenu(containerId, playerInventory), Component.translatable("babel"))
        );
    }
}
