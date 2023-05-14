package com.osariusz.paprysMapMod.keyBindings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final String modCategory = "key.category.papryusmapmod.papyrusmap";

    public static final KeyMapping openMapBinding = new KeyMapping("key.openMapBinding", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, modCategory);

    public static final KeyMapping mapPanningBinding = new KeyMapping("key.mapPanningBinding", KeyConflictContext.GUI,InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, modCategory);

    public static void register(RegisterKeyMappingsEvent event){
        event.register(KeyBindings.openMapBinding);
        event.register(KeyBindings.mapPanningBinding);
    }

}
