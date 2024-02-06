package com.osariusz.landmap.menus;


import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import static com.osariusz.landmap.LandMapMod.MAP_MENU;

public class MapMenu extends AbstractContainerMenu {


    public MapMenu(int containerId, Inventory playerInventory) {
        super(MAP_MENU.get(), containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
