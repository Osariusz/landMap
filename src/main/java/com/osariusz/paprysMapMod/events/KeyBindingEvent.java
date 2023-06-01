package com.osariusz.paprysMapMod.events;

import com.osariusz.paprysMapMod.keyBindings.KeyBindings;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyBindingEvent {

    @SubscribeEvent
    public void onKeyRegister(RegisterKeyMappingsEvent event) {
        KeyBindings.register(event);
    }
}
