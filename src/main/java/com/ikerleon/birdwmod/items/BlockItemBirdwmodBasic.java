package com.ikerleon.birdwmod.items;

import com.ikerleon.birdwmod.Main;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BlockItemBirdwmodBasic extends BlockItem {
    public BlockItemBirdwmodBasic(Block block, Settings settings) {
        super(block, settings);
        ItemGroupEvents.modifyEntriesEvent(Main.THE_BIRDWATCHING_MOD).register(content -> {
            content.add(this);
        });
    }
}
