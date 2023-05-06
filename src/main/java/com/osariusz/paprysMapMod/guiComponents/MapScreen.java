package com.osariusz.paprysMapMod.guiComponents;

import com.mojang.blaze3d.vertex.PoseStack;
import com.osariusz.paprysMapMod.client.ClientMapData;
import com.osariusz.paprysMapMod.menus.MapMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;


public class MapScreen extends AbstractContainerScreen<MapMenu> {

    MapTexture texture;

    public MapScreen(MapMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    public void newTexture(int width, int height){
        this.texture = new MapTexture(width,height);
    }

    @Override
    public void init() {
        super.init();
        this.font = Minecraft.getInstance().font;
        this.addRenderableWidget(new Button(10, 10, 20, 20, CommonComponents.GUI_CANCEL, (p_96057_) -> {
            this.minecraft.setScreen((Screen) null);
        }));
    }

    @Override
    public void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_){
    }

    public void scaleMap(PoseStack poseStack){
        double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        double xScale = (double)Minecraft.getInstance().getWindow().getWidth()/texture.width;
        double yScale = (double)Minecraft.getInstance().getWindow().getHeight()/texture.height;
        xScale /= guiScale;
        yScale /= guiScale;
        poseStack.scale((float)xScale,(float)yScale,1.0f);
    }

    @Override
    public void render(PoseStack poseStack, int p_96053_, int p_96054_, float p_96055_) {
        if(texture == null || (ClientMapData.getMapHeight() != texture.height || ClientMapData.getMapWidth() != texture.width)){
            newTexture(ClientMapData.getMapWidth(), ClientMapData.getMapHeight());
        }
        scaleMap(poseStack);
        texture.render(poseStack, ClientMapData.getLogicalMap());
        super.render(poseStack, p_96053_, p_96054_, p_96055_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }
}
