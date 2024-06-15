package com.ikerleon.birdwmod.mixin;

import com.ikerleon.birdwmod.items.ItemBinocular;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.*;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Final
    @Shadow
    private MinecraftClient client;

    @Unique
    private static final Identifier RIM = Identifier.of("birdwmod:textures/gui/binocularrim.png");

    @Inject(at = @At("TAIL"), method = "renderMiscOverlays")
    public void renderBinocularOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(client.player == null) return;
        if (client.player.getActiveHand() != null) {
            if (client.player.getStackInHand(client.player.getActiveHand()).getItem() instanceof ItemBinocular) {
                if (client.player.isUsingItem() && this.client.options.getPerspective() == Perspective.FIRST_PERSON) {
                    float f;
                    float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
                    float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g);
                    int i = MathHelper.floor(f * h);
                    int j = MathHelper.floor(g * h);
                    int k = (context.getScaledWindowWidth() - i) / 2;
                    int l = (context.getScaledWindowHeight() - j) / 2;
                    int m = k + i;
                    int n = l + j;
                    RenderSystem.enableBlend();
                    context.drawTexture(RIM, k, l, -90, 0.0f, 0.0f, i, j, i, j);
                    RenderSystem.disableBlend();
                    context.fill(RenderLayer.getGuiOverlay(), 0, n, context.getScaledWindowWidth(), context.getScaledWindowHeight(), -90, Colors.BLACK);
                    context.fill(RenderLayer.getGuiOverlay(), 0, 0, context.getScaledWindowWidth(), l, -90, Colors.BLACK);
                    context.fill(RenderLayer.getGuiOverlay(), 0, l, k, n, -90, Colors.BLACK);
                    context.fill(RenderLayer.getGuiOverlay(), m, l, context.getScaledWindowWidth(), n, -90, Colors.BLACK);

                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1);
                    RenderSystem.setShaderTexture(0, RIM);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    bufferBuilder.vertex(0.0F, context.getScaledWindowHeight(), -90.0F).texture(0.0F, 1.0F);
                    bufferBuilder.vertex(context.getScaledWindowWidth(), context.getScaledWindowHeight(), 0).texture(1.0F, 1.0F);
                    bufferBuilder.vertex(context.getScaledWindowWidth(), 0.0F, -90.0F).texture(1.0F, 0.0F);
                    bufferBuilder.vertex(0.0F, 0.0F, -90.0F).texture(0.0F, 0.0F);
                    BufferRenderer.draw(bufferBuilder.end());
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}