package com.osariusz.landmap.server;

import com.osariusz.landmap.configs.CommonConfig;
import com.osariusz.landmap.logicalMap.LogicalMap;
import com.osariusz.landmap.menus.MapMenu;
import com.osariusz.landmap.networking.LogicalMapMessages;
import com.osariusz.landmap.networking.packet.LogicalMapS2CPacket;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerMapData {

    private static ServerMapData INSTANCE = new ServerMapData();

    List<LogicalMap> logicalMaps = new ArrayList<>();

    int logicalMapWidth = CommonConfig.MAP_SEGMENTS_X.get();
    int logicalMapHeight = CommonConfig.MAP_SEGMENTS_Y.get();
    int xMapRadius = CommonConfig.MAP_XRADIUS.get();
    int yMapRadius = CommonConfig.MAP_YRADIUS.get();

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    public static ServerMapData getInstance() {
        return INSTANCE;
    }

    public LogicalMap getLogicalMap(ServerLevel level, Vec3 playerPosition) {
        for (LogicalMap logicalMap : logicalMaps) {
            if (logicalMap.isInside(playerPosition.x, playerPosition.z)) {
                return logicalMap;
            }
        }
        return prepareLogicalMap(level, playerPosition);
    }

    public LogicalMap prepareLogicalMap(ServerLevel level, Vec3 playerPosition) {
        int centreX = (int) (Math.round(playerPosition.x / (2 * xMapRadius)) * 2 * xMapRadius);
        int centreY = (int) (Math.round(playerPosition.z / (2 * yMapRadius)) * 2 * yMapRadius);
        LogicalMap result =
                LogicalMap.builder()
                        .center(new Vec3i(centreX, 0, centreY))
                        .radiusX(xMapRadius)
                        .radiusY(yMapRadius)
                        .mapSegmentsX(logicalMapWidth)
                        .mapSegmentsY(logicalMapHeight)
                        .build().generateIsWater(level);
        logicalMaps.add(result);
        return result;
    }

    public void playerMapClose(ServerPlayer player) {
        for(Runnable runnable : executor.getQueue()) {
            if(runnable instanceof ServerTask task) {
                if(task.getPlayerUUID().equals(player.getUUID())) {
                    executor.remove(runnable);
                }
            }
        }
    }

    public void playerMapOpen(ServerPlayer player) throws ExecutionException, InterruptedException {
        LogicalMap logicalMap;
        logicalMap = executor.submit(
                () -> getLogicalMap(player.getLevel(), player.position())
        ).get();
        System.out.println(executor.getQueue().toString());
        executor.submit(new ServerTask(
                        player.getUUID(),
                        () -> {
                            LogicalMapMessages.sendToPlayer(new LogicalMapS2CPacket(logicalMap), player);
                            NetworkHooks.openScreen(player, new SimpleMenuProvider(
                                    (containerId, playerInventory, playerLambda) -> new MapMenu(containerId, playerInventory), Component.translatable("babel"))
                            );
                        }
                )
        );
        System.out.println(executor.getQueue().toString());
    }
}
