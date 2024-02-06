package com.osariusz.landmap.server;

import lombok.Getter;
import lombok.ToString;

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
