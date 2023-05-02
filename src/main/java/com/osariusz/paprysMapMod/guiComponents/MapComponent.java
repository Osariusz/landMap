package com.osariusz.paprysMapMod.guiComponents;

import com.mojang.blaze3d.vertex.PoseStack;
import com.osariusz.paprysMapMod.client.LogicalMapData;
import com.osariusz.paprysMapMod.logicalMap.LogicalLine;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import com.osariusz.paprysMapMod.menus.MapMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class MapComponent extends AbstractContainerScreen<MapMenu> {

    List<LogicalLine> lines;
    public MapComponent(MapMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    public void init() {
        super.init();
        lines = LogicalMapData.getLogicalMap().getLines();
        this.font = Minecraft.getInstance().font;
        this.addRenderableWidget(new Button(this.width / 2 - 100, 140, 200, 20, CommonComponents.GUI_CANCEL, (p_96057_) -> {
            this.minecraft.setScreen((Screen) null);
        }));
    }

    public void render(PoseStack poseStack, int p_96053_, int p_96054_, float p_96055_) {
        //this.fillGradient(poseStack, 0, 0, this.width, this.height, -12574688, -11530224);
        this.fillGradient(poseStack, 0, 0, this.width, this.height, -2, -11530224);

        for(LogicalLine line : lines)
        {
            this.hLine(poseStack, line.getX1(), line.getX2(), line. getY(), line.getColor());
        }

        /*this.hLine(poseStack, 100, 300, 100, -2);
        this.hLine(poseStack, 100, 300, 99, -2);
        this.hLine(poseStack, 100, 300, 98, -2);*/

        /*for(LogicalLine logicalLine : logicalMap.getLines()){
            this.hLine(poseStack,logicalLine.getX1(),logicalLine.getX2(),logicalLine.getY(),logicalLine.getColor());
        }*/

        //drawCenteredString(poseStack, this.font, this.title, this.width / 2, 90, 16777215);
        //drawCenteredString(poseStack, this.font, "blebleble", this.width / 2, 110, 16777215);
        super.render(poseStack, p_96053_, p_96054_, p_96055_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }


    private static final ResourceLocation TEXTURE = new ResourceLocation("mapmenu", "textures/gui/mapmenu.png");
}
