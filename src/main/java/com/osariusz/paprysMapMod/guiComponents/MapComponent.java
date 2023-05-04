package com.osariusz.paprysMapMod.guiComponents;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.osariusz.paprysMapMod.client.LogicalMapData;
import com.osariusz.paprysMapMod.menus.MapMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;



public class MapComponent extends AbstractContainerScreen<MapMenu> {

    private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation("textures/gui/container/villager2.png");

    private DynamicTexture texture;
    List<List<Boolean>> isWater;

    static final int mapSegmentsX = 450;
    static final int mapSegmentsY = 251;


    RenderType renderType;
    public MapComponent(MapMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.texture = new DynamicTexture(mapSegmentsX,mapSegmentsY,true);
        ResourceLocation resourcelocation = Minecraft.getInstance().getTextureManager().register("oslar", this.texture);
        this.renderType = RenderType.text(resourcelocation);
    }

    public void init() {
        super.init();
        isWater = LogicalMapData.getLogicalMap().getIsWater();
        this.font = Minecraft.getInstance().font;
        this.addRenderableWidget(new Button(this.width / 2 - 100, 140, 200, 20, CommonComponents.GUI_CANCEL, (p_96057_) -> {
            this.minecraft.setScreen((Screen) null);
        }));
    }

    private void updateTexture() {
        for(int i = 0; i < mapSegmentsX; ++i) {
            for(int j = 0; j < mapSegmentsY; ++j) {
                int color = -12574688;
                if(isWater.get(i).get(j)){
                    color = -2;
                }
                if(i==mapSegmentsX/2 && j == mapSegmentsY/2){
                    color = -2000;
                }
                this.texture.getPixels().setPixelRGBA(i, j, color);
            }
        }

        this.texture.upload();
    }

    public void render(PoseStack poseStack, int p_96053_, int p_96054_, float p_96055_) {

        //this.fillGradient(poseStack, 0, 0, this.width, this.height, -12574688, -11530224);
        //this.fillGradient(poseStack, 0, 0, this.width, this.height, -2, -11530224);
        /*MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        VertexConsumer consumer = buffer.getBuffer(RenderType.lines());
        Matrix4f matrix4f = poseStack.last().pose();
        TextureAtlasSprite textureAtlasSprite = new TextureAtlasSprite();*/

            /*RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            blit(poseStack, this.leftPos + 83 + 99, this.topPos + 35, this.getBlitOffset(), 311.0F, 0.0F, 28, 21, 512, 256);*/

        updateTexture();

        int i = 0;
        int j = 0;
        float f = 0.0F;
        Matrix4f matrix4f = poseStack.last().pose();
        MultiBufferSource.BufferSource multiBufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        /*vertexconsumer.vertex(matrix4f, 0.0F, 128.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 128.0F, 128.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 128.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(15728880).endVertex();
        int k = 0;*/
        vertexconsumer.vertex(matrix4f, 0.0F, (float)mapSegmentsY, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float)mapSegmentsX, (float)mapSegmentsY, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float)mapSegmentsX, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(15728880).endVertex();


        multiBufferSource.endBatch();

        //for(LogicalLine line : lines)
        //{
            //GuiComponent.fill(poseStack,line.getX1(),line.getY(),line.getX2()+1,line.getY()+1,line.getColor());

            //this.hLine(poseStack, line.getX1(), line.getX2(), line. getY(), line.getColor());
        //}

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
