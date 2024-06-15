package com.ikerleon.birdwmod.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;


public class EricaBushBlock extends PlantBlock {
    public EricaBushBlock() {
        super(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return null;
    }
}
