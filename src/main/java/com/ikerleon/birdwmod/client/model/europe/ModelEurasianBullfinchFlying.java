package com.ikerleon.birdwmod.client.model.europe;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelEurasianBullfinchFlying - ikerleon02
 * Created using Tabula 7.0.0
 */
@SideOnly(Side.CLIENT)
public class ModelEurasianBullfinchFlying extends ModelEurasianBullfinch {

    public ModelEurasianBullfinchFlying() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.body.setRotationPoint(0.0F, 13.6F, -3.0F);
        this.body.addBox(-2.5F, 1.0F, 0.0F, 5, 4, 6, 0.0F);
        this.setRotateAngle(body, 0.0F, 0.0F, 0.0F);
        this.belly3.setRotationPoint(0.0F, 4.9F, 8.7F);
        this.belly3.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.setRotateAngle(belly3, -1.3962634015954636F, 0.0F, 0.0F);
        this.belly2.setRotationPoint(0.0F, 5.0F, 0.1F);
        this.belly2.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 3, 0.0F);
        this.setRotateAngle(belly2, 1.3089969389957472F, 0.0F, 0.0F);
        this.rightwing_1.setRotationPoint(2.0F, 2.9F, 3.1F);
        this.rightwing_1.addBox(0.0F, -1.5F, -1.5F, 1, 4, 6, 0.0F);
        this.setRotateAngle(rightwing_1, -1.2217304763960306F, 0.0F, -1.48352986419518F);
        this.rightwing2_1.setRotationPoint(-0.1F, -0.9F, 3.5F);
        this.rightwing2_1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
        this.setRotateAngle(rightwing2_1, -0.08726646259971647F, -0.08726646259971647F, 0.0F);
        this.body2.setRotationPoint(0.0F, 1.1F, 5.3F);
        this.body2.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 5, 0.0F);
        this.setRotateAngle(body2, -0.17453292519943295F, 0.0F, 0.0F);
        this.rightfoot.setRotationPoint(0.0F, 4.4F, -0.6F);
        this.rightfoot.addBox(-1.0F, 0.0F, -2.5F, 2, 1, 3, 0.0F);
        this.setRotateAngle(rightfoot, -1.5707963267948966F, 0.0F, 0.0F);
        this.neckfront.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.neckfront.addBox(-1.5F, -2.0F, -3.0F, 3, 2, 3, 0.0F);
        this.setRotateAngle(neckfront, 0.8726646259971648F, 0.0F, 0.0F);
        this.belly.setRotationPoint(0.0F, 4.9F, 0.0F);
        this.belly.addBox(-2.0F, 0.0F, -3.0F, 4, 2, 3, 0.0F);
        this.setRotateAngle(belly, -2.443460952792061F, 0.0F, 0.0F);
        this.rightwing3_1.setRotationPoint(-0.1F, 0.6F, 3.5F);
        this.rightwing3_1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(rightwing3_1, 0.08726646259971647F, 0.0F, 0.0F);
        this.rightwing2.setRotationPoint(-0.9F, -0.9F, 2.5F);
        this.rightwing2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
        this.setRotateAngle(rightwing2, -0.08726646259971647F, 0.08726646259971647F, 0.0F);
        this.headtop4.setRotationPoint(0.0F, -3.3F, -2.0F);
        this.headtop4.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.beak.setRotationPoint(0.0F, -1.3F, 0.8F);
        this.beak.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(beak, 0.3490658503988659F, 0.0F, 0.0F);
        this.tailleft.setRotationPoint(0.4F, 0.4F, 4.5F);
        this.tailleft.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(tailleft, 0.08726646259971647F, 0.08726646259971647F, 0.17453292519943295F);
        this.headfront.setRotationPoint(0.0F, -0.5F, -4.3F);
        this.headfront.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 1, 0.0F);
        this.beakbase.setRotationPoint(0.0F, -0.4F, -0.1F);
        this.beakbase.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(beakbase, -0.17453292519943295F, 0.0F, 0.0F);
        this.leftfoot.setRotationPoint(0.0F, 4.4F, -0.6F);
        this.leftfoot.addBox(-1.0F, 0.0F, -2.5F, 2, 1, 3, 0.0F);
        this.setRotateAngle(leftfoot, -1.5707963267948966F, 0.0F, 0.0F);
        this.shape13.setRotationPoint(0.0F, 1.7F, 8.2F);
        this.shape13.addBox(-1.5F, 0.0F, -8.0F, 3, 3, 8, 0.0F);
        this.setRotateAngle(shape13, -0.31869712141416456F, 0.0F, 0.0F);
        this.head.setRotationPoint(0.0F, 1.2F, -6.3F);
        this.head.addBox(-2.0F, -3.0F, -4.0F, 4, 3, 4, 0.0F);
        this.setRotateAngle(head, 0.4363323129985824F, 0.0F, 0.0F);
        this.leftleg.setRotationPoint(1.4F, 2.7F, 5.7F);
        this.leftleg.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(leftleg, 1.5707963267948966F, 0.0F, 0.0F);
        this.rightwing.setRotationPoint(-2.0F, 2.9F, 3.1F);
        this.rightwing.addBox(-1.0F, -1.5F, -1.5F, 1, 4, 6, 0.0F);
        this.setRotateAngle(rightwing, -1.2217304763960306F, 0.0F, 1.48352986419518F);
        this.tailmiddle.setRotationPoint(0.0F, 0.3F, 4.5F);
        this.tailmiddle.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(tailmiddle, 0.08726646259971647F, 0.0F, 0.0F);
        this.rightwing3.setRotationPoint(0.1F, 0.6F, 3.5F);
        this.rightwing3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(rightwing3, 0.08726646259971647F, 0.0F, 0.0F);
        this.tailright.setRotationPoint(-0.4F, 0.4F, 4.5F);
        this.tailright.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(tailright, 0.08726646259971647F, -0.08726646259971647F, -0.17453292519943295F);
        this.rightleg.setRotationPoint(-1.4F, 2.7F, 5.7F);
        this.rightleg.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(rightleg, 1.5707963267948966F, 0.0F, 0.0F);
    }
}
