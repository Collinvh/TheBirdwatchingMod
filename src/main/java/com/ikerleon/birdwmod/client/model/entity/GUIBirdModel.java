package com.ikerleon.birdwmod.client.model.entity;

import com.ikerleon.birdwmod.Main;
import com.ikerleon.birdwmod.entity.BirdEntity;
import net.minecraft.util.Identifier;

public class GUIBirdModel extends BirdBaseModel
{
    private static final int ticksTilShift = 50;

    public GUIBirdModel(){ super();}

    public static int getVariantAtAge(long worldTime, int numVariants){
        if(numVariants == 1){return 1;}
        return (int)(worldTime % ((numVariants) * ticksTilShift))/ticksTilShift;
    }

    @Override
    public Identifier getTextureResource(BirdEntity rawEntity) {
        String path = rawEntity.getPath() + "/" + rawEntity.getPath();
        int variant;
        if (rawEntity.isDimorphic() && rawEntity.getGender() == 1){
            path += "_female";
            variant = getVariantAtAge(rawEntity.getWorld().getTime(), rawEntity.getBirdVariantsFemaleSpecific());
        } else {
            variant = getVariantAtAge(rawEntity.getWorld().getTime(), rawEntity.getBirdVariants());
        }
        if(variant > 1){
            path += "_" + variant;
        }
        return Identifier.of(Main.ModID, buildTexturePath(path));
    }
}