package com.ikerleon.birdwmod.client.model.entity;

import com.ikerleon.birdwmod.Main;

import com.ikerleon.birdwmod.entity.BirdEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class BirdBaseModel extends GeoModel<BirdEntity>
{
    public BirdBaseModel(){ super();}

    @Override
    public Identifier getAnimationResource(BirdEntity animatable) {
        return Identifier.of(Main.ModID, buildAnimationPath(animatable.getPath()));
    }

    @Override
    public Identifier getTextureResource(BirdEntity animatable) {
        BirdEntity entity = animatable;
        String path = entity.getPath() + "/" + entity.getPath();
        if (entity.isDimorphic() && entity.getGender() == 1 ){path += "_female";}
        if (entity.getVariant() >= 2) { path += ("_"+entity.getVariant()); }
        return Identifier.of(Main.ModID, buildTexturePath(path));
    }

    @Override
    public Identifier getModelResource(BirdEntity animatable) {
        return Identifier.of(Main.ModID, buildModelPath(animatable.getPath()));
    }

    protected static String buildModelPath(String path){
        return "geo/"+path+".geo.json";
    }

    protected static String buildTexturePath(String path){
        return "textures/entity/"+path+".png";
    }

    protected static String buildAnimationPath(String path){
        return "animations/"+path+".animation.json";
    }
}