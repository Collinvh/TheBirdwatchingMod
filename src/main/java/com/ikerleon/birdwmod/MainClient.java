package com.ikerleon.birdwmod;

import com.ikerleon.birdwmod.blocks.InitBlocks;
//import com.ikerleon.birdwmod.entity.InitEntities;
import com.ikerleon.birdwmod.entity.InitEntities;
import com.ikerleon.birdwmod.items.InitItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(InitBlocks.RINGING_NET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(InitBlocks.BIRD_FEEDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(InitBlocks.LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(InitBlocks.ERICA_BUSH, RenderLayer.getCutout());

        ModelPredicateProviderRegistry.register(InitItems.BINOCULAR_BASIC, Identifier.ofVanilla("pulling"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        ModelPredicateProviderRegistry.register(InitItems.BINOCULAR_MEDIUM, Identifier.ofVanilla("pulling"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        ModelPredicateProviderRegistry.register(InitItems.BINOCULAR_PROFFESIONAL, Identifier.ofVanilla("pulling"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        InitEntities.registerRenderers();
    }
}
