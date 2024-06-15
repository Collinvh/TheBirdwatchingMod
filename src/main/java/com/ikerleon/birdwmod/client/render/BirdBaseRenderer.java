package com.ikerleon.birdwmod.client.render;

import com.ikerleon.birdwmod.Main;
import com.ikerleon.birdwmod.client.model.entity.BirdBaseModel;
import com.ikerleon.birdwmod.entity.BirdEntity;
import com.ikerleon.birdwmod.entity.BirdSettings;
import com.ikerleon.birdwmod.entity.InitEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class BirdBaseRenderer extends GeoEntityRenderer<BirdEntity> {

    public BirdBaseRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BirdBaseModel());
//        this.renderLayers.addLayer(new BlinkSleepingFeature(this));
//        this.renderLayers.addLayer(new RingFeature(this));
    }

    public Identifier getBlinkTexture(BirdEntity entity)
    {
        return Identifier.of(Main.ModID, "textures/entity/"+entity.getPath()+"/" + entity.getPath() + "_sleeping.png");
    }

    public Identifier getRingTexture(BirdEntity entity)
    {
        return Identifier.of(Main.ModID, "textures/entity/rings/"+entity.getPath()+"_ring.png");
    }

    @Override
    public void preRender(MatrixStack poseStack, BirdEntity animatable, BakedGeoModel model, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.scale(animatable.getScaleFactor(),animatable.getScaleFactor(),animatable.getScaleFactor());
        if(animatable.isBaby()){
            poseStack.scale(0.5F,0.5F,0.5F);
        }
        if(animatable.getPath().equals("hoatzin")){
            poseStack.translate(0F, -0.075, 0F);
        }
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    //    @Environment(EnvType.CLIENT)
//    public class BlinkSleepingFeature extends GeoRenderLayer<BirdEntity> {
//        private final BirdBaseRenderer render;
//
//        public BlinkSleepingFeature(BirdBaseRenderer ctx) {
//            super(ctx);
//            this.render = ctx;
//        }
//
//        @Override
//        public void preRender(MatrixStack poseStack, BirdEntity birdEntity, BakedGeoModel bakedModel, @Nullable RenderLayer renderType, VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
//            if ((!birdEntity.isInvisible() && (birdEntity.getBlinking()) || birdEntity.isSleeping()) && birdEntity.isOnGround() && this.render.getBlinkTexture(birdEntity) != null)  {
//                render(render.getGeoModel(), animatable, bakedModel, renderType, bufferSource, buffer, LivingEntityRenderer.getOverlay(birdEntity, 0.0F), 1, 1, 1);
//            }
//        }
//    }
//
//    @Environment(EnvType.CLIENT)
//    public class RingFeature extends GeoRenderLayer<BirdEntity>{
//
//        private final BirdBaseRenderer birdRender;
//
//        public RingFeature(BirdBaseRenderer ctx) {
//            super(ctx);
//            this.birdRender = ctx;
//        }
//
//        @Override
//        public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BirdEntity birdEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgres, float headYaw, float headPitch) {
//            if (!birdEntity.isInvisible() && birdEntity.hasBeenRinged() && birdRender.getRingTexture(birdEntity) != null)
//            {
//                float[] fs = birdEntity.getRingColor().getColorComponents();
//                renderModel(birdRender.getGeoModelProvider(), getRingTexture(birdEntity), matrixStack, vertexConsumerProvider, i, birdEntity, 1, fs[0], fs[1], fs[2]);
//            }
//        }
//    }
}