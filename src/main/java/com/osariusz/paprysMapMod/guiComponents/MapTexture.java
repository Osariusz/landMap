package com.osariusz.paprysMapMod.guiComponents;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.osariusz.paprysMapMod.logicalMap.LogicalMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class MapTexture {

    protected int width;
    protected int height;

    protected DynamicTexture texture;

    protected RenderType renderType;

    public MapTexture(int width, int height){
        this.width = width;
        this.height = height;
        this.texture = new DynamicTexture(width,height,true);
        ResourceLocation resourcelocation = Minecraft.getInstance().getTextureManager().register("oslar", this.texture);
        this.renderType = RenderType.text(resourcelocation);
    }

    public int getMapColor(int R, int G, int B, int Alpha){
        Color color = new Color(B,G,R,Alpha);
        return color.getRGB();
    }

    public int waterColor(){
        return getMapColor(52,166,218,255);
    }
    public int landColor(){
        return getMapColor(75,25,25,255);
    }
    public int playerColor(){
        return getMapColor(250,255,10,255);
    }

    public void updateTexture(LogicalMap logicalMap){
        int logicalWidth = logicalMap.getWidth();
        int logicalHeight = logicalMap.getHeight();

        int xOffset = Math.max(0,logicalWidth-width);
        int yOffset = Math.max(0,logicalHeight-height);

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                int color = landColor();
                if(logicalMap.isWater(x+xOffset/2,y+yOffset/2)){
                    color = waterColor();
                }
                if(x==width/2 && y == height/2){
                    color = playerColor();
                }
                this.texture.getPixels().setPixelRGBA(x, y, color);
            }
        }
        this.texture.upload();
    }

    public void render(PoseStack poseStack, LogicalMap logicalMap){
        updateTexture(logicalMap);
        Matrix4f matrix4f = poseStack.last().pose();
        MultiBufferSource.BufferSource multiBufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        vertexconsumer.vertex(matrix4f, 0.0F, (float)height, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float)width, (float)height, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float)width, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(15728880).endVertex();
        multiBufferSource.endBatch();
    }
}
