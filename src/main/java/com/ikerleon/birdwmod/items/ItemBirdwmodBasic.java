package com.ikerleon.birdwmod.items;

import com.ikerleon.birdwmod.Main;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;

public class ItemBirdwmodBasic extends Item {
    public ItemBirdwmodBasic() {
        this(new Item.Settings());
    }

    public ItemBirdwmodBasic(Item.Settings settings) {
        super(settings);

        ItemGroupEvents.modifyEntriesEvent(Main.THE_BIRDWATCHING_MOD).register(content -> {
            content.add(this);
        });
    }
}
