package com.osariusz.paprysMapMod.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.osariusz.paprysMapMod.client.ClientMapData;
import com.osariusz.paprysMapMod.guiComponents.MapTexture;
import com.osariusz.paprysMapMod.keyBindings.KeyBindings;
import com.osariusz.paprysMapMod.networking.LogicalMapMessages;
import com.osariusz.paprysMapMod.networking.packet.RequestMapC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEvents {

   @SubscribeEvent
   public void render(TickEvent.RenderTickEvent event){
        //debug measure
       if(Minecraft.getInstance().options.keyDrop.isDown()){
           System.out.println("inputed render");

           MapTexture mapTexture = new MapTexture(ClientMapData.getInstance().getMapWidth(),ClientMapData.getInstance().getMapHeight(),1.0f);
           PoseStack poseStack = new PoseStack();
           double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
           poseStack.scale(1/(float)guiScale,1/(float)guiScale,1/(float)guiScale);
           mapTexture.render(poseStack,ClientMapData.getInstance().getLogicalMap(),0,0);


       }
   }

   @SubscribeEvent
   public void onClientTick(TickEvent.ClientTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            while(KeyBindings.mapPanningBinding.consumeClick()){
                System.out.println("konono");
            }
        }
   }


   @SubscribeEvent
   public void onKeyInput(InputEvent event){
        if(KeyBindings.openMapBinding.consumeClick()){
            System.out.println("inputed");
            LogicalMapMessages.sendToServer(new RequestMapC2SPacket());
        }

   }
}
