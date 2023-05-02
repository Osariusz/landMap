package com.osariusz.paprysMapMod.events;

import com.osariusz.paprysMapMod.guiComponents.MapComponent;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import com.osariusz.paprysMapMod.menus.MapMenu;
import com.osariusz.paprysMapMod.networking.LogicalMapMessages;
import com.osariusz.paprysMapMod.networking.packet.LogicalMapS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;

public class ModEvents {

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
        System.out.println("blockclick");
        LogicalMap logicalMap = new LogicalMap(event.getLevel(),event.getPos(),10000,10000);
       LogicalMapMessages.sendToPlayer(new LogicalMapS2CPacket(logicalMap),(ServerPlayer) event.getEntity());
       NetworkHooks.openScreen((ServerPlayer) event.getEntity(),new SimpleMenuProvider(
               (containerId, playerInventory, player) -> new MapMenu(containerId,playerInventory),Component.translatable("babel"))
       );
   }

   @SubscribeEvent
   public void onKeyInput(InputEvent event){

        if(Minecraft.getInstance().options.keyDrop.isDown()){
            System.out.println("inputed");

            //Minecraft.getInstance().setScreen(new MapComponent(Component.translatable("Minimapa")));
        }
   }
}
