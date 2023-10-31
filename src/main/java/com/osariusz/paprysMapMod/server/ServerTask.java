package com.osariusz.paprysMapMod.server;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.openjdk.nashorn.internal.objects.annotations.Constructor;

import java.util.UUID;

@Getter
@ToString
public class ServerTask implements Runnable {

    private final UUID playerUUID;
    private final Runnable runnable;

    public ServerTask(UUID uuid, Runnable runnable) {
        this.playerUUID = uuid;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
