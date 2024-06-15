package com.ikerleon.birdwmod.world.feature.placer;

import com.terraformersmc.terraform.tree.placer.PlacerTypes;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class TBMPlacerTypes {
    public static final FoliagePlacerType<NoneFoliagePlacer> NONE_FOLIAGE_PLACER = PlacerTypes.registerFoliagePlacer(Identifier.of("birdwmod:none"), NoneFoliagePlacer.CODEC);
    public static final TrunkPlacerType<FallenTrunkPlacer> FALLEN_TRUNK_PLACER = PlacerTypes.registerTrunkPlacer(Identifier.of("traverse:fallen"), FallenTrunkPlacer.CODEC);
}
