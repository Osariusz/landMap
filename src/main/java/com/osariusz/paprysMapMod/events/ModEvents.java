package com.osariusz.paprysMapMod.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.osariusz.paprysMapMod.client.ClientMapData;
import com.osariusz.paprysMapMod.guiComponents.MapScreen;
import com.osariusz.paprysMapMod.guiComponents.MapTexture;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import com.osariusz.paprysMapMod.menus.MapMenu;
import com.osariusz.paprysMapMod.networking.LogicalMapMessages;
import com.osariusz.paprysMapMod.networking.packet.LogicalMapS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class ModEvents {

    LogicalMap logicalMap = null;

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        if(!player.level.isClientSide)
        {
            //System.out.println(player.getLevel().getBiome(player.getOnPos()));
            //System.out.println(player.getLevel().getBiome(player.getOnPos().offset(new Vec3i(10000,0,10000))));
        }
   }

   @SubscribeEvent
   public void onBlockClick(PlayerInteractEvent.RightClickBlock event){
        if(!(event.getEntity() instanceof ServerPlayer)){
            return;
        }
        int width = 1000;
        int height = 500;
        if(true){
            logicalMap = new LogicalMap(event.getLevel(),event.getPos(),10000,10000,width,height);
        }
        System.out.println("blockclick");
       LogicalMapMessages.sendToPlayer(new LogicalMapS2CPacket(logicalMap),(ServerPlayer) event.getEntity());
       NetworkHooks.openScreen((ServerPlayer) event.getEntity(),new SimpleMenuProvider(
               (containerId, playerInventory, player) -> new MapMenu(containerId,playerInventory),Component.translatable("babel"))
       );
       System.out.println("blockclick2");
   }

   @SubscribeEvent
   public void render(TickEvent.RenderTickEvent event){
       if(Minecraft.getInstance().options.keyDrop.isDown()){
           System.out.println("inputed render");

           MapTexture mapTexture = new MapTexture(ClientMapData.getMapWidth(),ClientMapData.getMapHeight());
           PoseStack poseStack = new PoseStack();
           double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
           poseStack.scale(1/(float)guiScale,1/(float)guiScale,1/(float)guiScale);
           mapTexture.render(poseStack,ClientMapData.getLogicalMap());


       }
   }

   @SubscribeEvent
   public void onKeyInput(InputEvent event){

        if(Minecraft.getInstance().options.keyDrop.isDown()){
            System.out.println("inputed");

            //Minecraft.getInstance().setScreen(new MapScreen(Component.translatable("Minimapa")));
        }
   }
}
