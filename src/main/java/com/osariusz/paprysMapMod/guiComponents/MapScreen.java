package com.osariusz.paprysMapMod.guiComponents;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.osariusz.paprysMapMod.client.ClientMapData;
import com.osariusz.paprysMapMod.keyBindings.KeyBindings;
import com.osariusz.paprysMapMod.menus.MapMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.event.InputEvent;


public class MapScreen extends AbstractContainerScreen<MapMenu> {

    @Override
    public boolean mouseScrolled(double x, double y, double scrollAmount) {
        ClientMapData.getInstance().addMapScale((float)scrollAmount*0.1f);
        return true;
    }

    MapTexture texture;

    public MapScreen(MapMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    public void newTexture(int width, int height, float scale){
        this.texture = new MapTexture(width,height, scale);
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

    public void scaleMap(PoseStack poseStack, float scale){
        double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        double xScale = (double)Minecraft.getInstance().getWindow().getWidth()/texture.width;
        double yScale = (double)Minecraft.getInstance().getWindow().getHeight()/texture.height;
        xScale /= guiScale;
        yScale /= guiScale;
        xScale *= scale;
        yScale *= scale;
        poseStack.scale((float)xScale,(float)yScale,1.0f);
    }

    @Override
    public void render(PoseStack poseStack, int p_96053_, int p_96054_, float p_96055_) {
        if(texture == null || (ClientMapData.getInstance().getMapHeight() != texture.height || ClientMapData.getInstance().getMapWidth() != texture.width)){
            newTexture((int)(ClientMapData.getInstance().getMapWidth()/ClientMapData.getInstance().getMapScale()), (int)(ClientMapData.getInstance().getMapHeight()/ClientMapData.getInstance().getMapScale()), ClientMapData.getInstance().getMapScale());
        }
        PoseStack mapPoseStack = new PoseStack();
        scaleMap(mapPoseStack, ClientMapData.getInstance().getMapScale());
        texture.render(mapPoseStack, ClientMapData.getInstance().getLogicalMap(),ClientMapData.getInstance().getxOffset(),ClientMapData.getInstance().getyOffset());
        super.render(poseStack, p_96053_, p_96054_, p_96055_);
    }

    /*@Override
    public boolean mouseClicked(double x, double y, int button){
        if(KeyBindings.mapPanningBinding.isActiveAndMatches(InputConstants.Type.MOUSE.getOrCreate(button))){
            panning = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button){
        if(panning && !KeyBindings.mapPanningBinding.isActiveAndMatches(InputConstants.Type.MOUSE.getOrCreate(button))){
            panning = false;
            return true;
        }
        return false;
    }*/

    @Override
    public boolean mouseDragged(double x, double y, int type, double xDistance, double yDistance){
        if(KeyBindings.mapPanningBinding.isActiveAndMatches(InputConstants.Type.MOUSE.getOrCreate(type))){
            double xDiff = xDistance;
            double yDiff = yDistance;
            pan(xDiff, yDiff);
            return true;
        }
        return false;
    }

    public void pan(double x, double y){
        x = -x;
        y = -y;
        if(x<0){
            x = Math.floor(x);
        }
        if(y<0){
            y = Math.floor(y);
        }
        ClientMapData.getInstance().addxOffset((int)Math.ceil(x));
        ClientMapData.getInstance().addyOffset((int)Math.ceil(y));
    }


    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }
}
