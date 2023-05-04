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
        updateTexture();

        int i = 0;
        int j = 0;
        float f = 0.0F;
        Matrix4f matrix4f = poseStack.last().pose();
        MultiBufferSource.BufferSource multiBufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        vertexconsumer.vertex(matrix4f, 0.0F, (float)mapSegmentsY, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float)mapSegmentsX, (float)mapSegmentsY, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float)mapSegmentsX, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(15728880).endVertex();


        multiBufferSource.endBatch();
        super.render(poseStack, p_96053_, p_96054_, p_96055_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }


    private static final ResourceLocation TEXTURE = new ResourceLocation("mapmenu", "textures/gui/mapmenu.png");
}
