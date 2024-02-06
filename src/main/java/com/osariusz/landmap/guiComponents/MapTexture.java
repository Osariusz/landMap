package com.osariusz.landmap.guiComponents;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.osariusz.landmap.configs.ClientConfig;
import com.osariusz.landmap.logicalMap.LogicalMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class MapTexture {

    protected int width;
    protected int height;

    public float getScale() {
        return scale;
    }

    protected float scale;

    protected DynamicTexture texture;

    protected RenderType renderType;

    public MapTexture(int width, int height, float scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.texture = new DynamicTexture(this.width, this.height, true);
        ResourceLocation resourcelocation = Minecraft.getInstance().getTextureManager().register("oslar", this.texture);
        this.renderType = RenderType.text(resourcelocation);
    }

    public int getMapColor(int R, int G, int B, int Alpha) {
        Color color = new Color(B, G, R, Alpha);
        return color.getRGB();
    }

    public int waterColor() {
        return getMapColor(ClientConfig.WATER_COLOR_R.get(), ClientConfig.WATER_COLOR_G.get(), ClientConfig.WATER_COLOR_B.get(), 255);
    }

    public int landColor() {
        return getMapColor(ClientConfig.LAND_COLOR_R.get(), ClientConfig.LAND_COLOR_G.get(), ClientConfig.LAND_COLOR_B.get(), 255);
    }

    public int playerColor() {
        return getMapColor(ClientConfig.PLAYER_COLOR_R.get(), ClientConfig.PLAYER_COLOR_G.get(), ClientConfig.PLAYER_COLOR_B.get(), 255);
    }

    public int backgroundColor() {
        return getMapColor(0, 0, 0, 0);
    }

    public Integer getMapPixelColor(LogicalMap logicalMap, int x, int y) {
        if (x < 0 || y < 0 || x >= logicalMap.getMapSegmentsX() || y >= logicalMap.getMapSegmentsY()) {
            return landColor();
        }
        int color = landColor();
        if (logicalMap.isWater(x, y)) {
            color = waterColor();
        }
        return color;
    }

    public Integer getGeneralPixelColor(LogicalMap logicalMap, int x, int y, int xCameraOffset, int yCameraOffset, int xCentreAdjustment, int yCentreAdjustment) {
        if (x == width / 2 && y == height / 2) {
            return playerColor();
        }
        return getMapPixelColor(logicalMap, x + xCameraOffset + xCentreAdjustment / 2, y + yCameraOffset + yCentreAdjustment / 2);
    }

    public void updateTexture(LogicalMap logicalMap, int xCameraOffset, int yCameraOffset) {
        int logicalWidth = logicalMap.getWidth();
        int logicalHeight = logicalMap.getHeight();

        int xCentreAdjustment = Math.max(0, logicalWidth - width);
        int yCentreAdjustment = Math.max(0, logicalHeight - height);

        for (int x = 0; x < (int) (width); ++x) {
            for (int y = 0; y < (int) (height); ++y) {
                Integer color = getGeneralPixelColor(logicalMap, x, y, xCameraOffset, yCameraOffset, xCentreAdjustment, yCentreAdjustment);
                if (color != null) {
                    this.texture.getPixels().setPixelRGBA(x, y, color);
                }
            }
        }
        this.texture.upload();
    }

    public void render(PoseStack poseStack, LogicalMap logicalMap, int xOffset, int yOffset) {
        updateTexture(logicalMap, xOffset, yOffset);
        Matrix4f matrix4f = poseStack.last().pose();
        MultiBufferSource.BufferSource multiBufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        vertexconsumer.vertex(matrix4f, 0.0F, (float) height, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float) width, (float) height, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, (float) width, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(15728880).endVertex();
        vertexconsumer.vertex(matrix4f, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(15728880).endVertex();
        multiBufferSource.endBatch();
    }
}
