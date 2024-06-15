package com.ikerleon.birdwmod.blocks;

import com.ikerleon.birdwmod.Main;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class InitBlocks {

    public static final RingingNetBlock RINGING_NET = new RingingNetBlock();
    public static final BirdFeederBlock BIRD_FEEDER = new BirdFeederBlock();
    public static final LichenBlock LICHEN = new LichenBlock();
    public static final EricaBushBlock ERICA_BUSH = new EricaBushBlock();

    public static void registerBlocks(){
        Registry.register(Registries.BLOCK, Identifier.of("birdwmod", "ringingnet"), RINGING_NET);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "ringingnet"), new BlockItem(RINGING_NET, new Item.Settings()));

        Registry.register(Registries.BLOCK, Identifier.of("birdwmod", "birdfeeder"), BIRD_FEEDER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "birdfeeder"), new BlockItem(BIRD_FEEDER, new Item.Settings()));

        Registry.register(Registries.BLOCK, Identifier.of("birdwmod", "lichen"), LICHEN);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "lichen"), new BlockItem(LICHEN, new Item.Settings()));

        Registry.register(Registries.BLOCK, Identifier.of("birdwmod", "ericabush"), ERICA_BUSH);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "ericabush"), new BlockItem(ERICA_BUSH, new Item.Settings()));
    }
}
