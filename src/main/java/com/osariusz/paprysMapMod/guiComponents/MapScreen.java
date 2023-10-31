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
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.awt.geom.Point2D;


public class MapScreen extends AbstractContainerScreen<MapMenu> {

    @Override
    public boolean mouseScrolled(double x, double y, double scrollAmount) {
        ClientMapData.getInstance().addMapScale((float) scrollAmount * 0.1f);
        return true;
    }

    MapTexture texture;

    public MapScreen(MapMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    public void newTexture(int width, int height, float scale) {
        this.texture = new MapTexture(width, height, scale);
    }

    @Override
    public void init() {
        super.init();
        this.font = Minecraft.getInstance().font;
        this.addRenderableWidget(new Button(10, 10, 40, 20, CommonComponents.GUI_CANCEL, (p_96057_) -> {
            this.minecraft.setScreen((Screen) null);
        }));
    }

    @Override
    public void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
    }

    public Point2D.Double mouseToMapCoordinates(Point2D.Double mousePositions){
        
        double x = ((ClientMapData.getInstance().getMapWidth()/(double)width)*mousePositions.x)/ClientMapData.getInstance().getMapScale();
        double y = ((ClientMapData.getInstance().getMapHeight()/(double)height)*mousePositions.y)/ClientMapData.getInstance().getMapScale();
        return new Point2D.Double(x,y);
    }

    public void scaleMap(PoseStack poseStack, float scale) {
        double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        double xScale = (double) Minecraft.getInstance().getWindow().getWidth() / texture.width;
        double yScale = (double) Minecraft.getInstance().getWindow().getHeight() / texture.height;
        xScale /= guiScale;
        yScale /= guiScale;
        xScale *= scale;
        yScale *= scale;
        poseStack.scale((float) xScale, (float) yScale, 1.0f);
    }

    public void translateMap(PoseStack poseStack, double x, double y) {
        poseStack.translate(-x, -y, 0.0D);
    }

    public void renderCoordinateTooltip(PoseStack poseStack, int mouseX, int mouseY){
        Point2D.Double blockCoordinates = ClientMapData.getInstance().mapToCentreBlockCoordinates(mouseToMapCoordinates(new Point2D.Double((float)mouseX,(float)mouseY)));
        int renderMouseX = mouseX;
        int renderMouseY = mouseY;
        if(renderMouseY < 20){
            renderMouseY = 20;
        }


        this.renderTooltip(
                poseStack,
                Component.literal(String.format("%d %d",(int)blockCoordinates.x,(int)blockCoordinates.y)),
                renderMouseX,
                renderMouseY
        );
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {

        if (texture == null || (ClientMapData.getInstance().getMapHeight() != texture.height || ClientMapData.getInstance().getMapWidth() != texture.width)) {
            System.out.println(ClientMapData.getInstance().getMapScale());
            newTexture((int) (ClientMapData.getInstance().getMapWidth()), (int) (ClientMapData.getInstance().getMapHeight()), ClientMapData.getInstance().getMapScale());
        }
        PoseStack mapPoseStack = new PoseStack();
        scaleMap(mapPoseStack, ClientMapData.getInstance().getMapScale());
        translateMap(mapPoseStack, ClientMapData.getInstance().getXOffset(), ClientMapData.getInstance().getYOffset());
        Point2D.Double playerOnMap = ClientMapData.getInstance().getPlayerPositionOnMap();
        texture.render(mapPoseStack, ClientMapData.getInstance().getLogicalMap(), (int)playerOnMap.x, (int)playerOnMap.y);
        renderCoordinateTooltip(poseStack, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseDragged(double x, double y, int type, double xDiff, double yDiff) {
        if (KeyBindings.mapPanningBinding.isActiveAndMatches(InputConstants.Type.MOUSE.getOrCreate(type))) {
            pan(xDiff, yDiff);
            return true;
        }
        return false;
    }

    public void pan(double x, double y) {
        x = -x;
        y = -y;
        if (x < 0) {
            x = Math.floor(x);
        }
        if (y < 0) {
            y = Math.floor(y);
        }
        ClientMapData.getInstance().addxOffset((Math.ceil(x) / ClientMapData.getInstance().getMapScale()));
        ClientMapData.getInstance().addyOffset((Math.ceil(y) / ClientMapData.getInstance().getMapScale()));
    }


    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }
}
