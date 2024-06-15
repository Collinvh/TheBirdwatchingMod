package com.ikerleon.birdwmod.mixin;

import com.ikerleon.birdwmod.items.ItemBinocular;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.Perspective;
import net.minecraft.util.math.Smoother;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow private boolean cursorLocked;

    @Shadow @Final private MinecraftClient client;

    @Shadow
    private final Smoother cursorXSmoother = new Smoother();

    @Shadow
    private final Smoother cursorYSmoother = new Smoother();

    @Shadow private double cursorDeltaX;

    @Shadow private double cursorDeltaY;

    @Inject(at = @At("TAIL"), method = "updateMouse")
    public void SmoothMouse(CallbackInfo info) {
        if(client.player == null) return;
        if (this.cursorLocked && this.client.isWindowFocused()) {
            double f = this.client.options.getMouseSensitivity().getValue() * 0.6000000238418579D + 0.20000000298023224D;
            double g = f * f * f;
            double h = g * 8.0D;
            double o;
            double p ;
            if(client.player.getActiveHand() != null) {
                if (client.player.getStackInHand(client.player.getActiveHand()).getItem() instanceof ItemBinocular && client.player.isUsingItem() && this.client.options.getPerspective() == Perspective.FIRST_PERSON) {
                    this.cursorXSmoother.clear();
                    this.cursorYSmoother.clear();
                    o = this.cursorDeltaX * g;
                    p = this.cursorDeltaY * g;
                }
                else {
                    this.cursorXSmoother.clear();
                    this.cursorYSmoother.clear();
                    o = this.cursorDeltaX * h;
                    p = this.cursorDeltaY * h;
                }

                this.cursorDeltaX = 0.0D;
                this.cursorDeltaY = 0.0D;
                int q = 1;
                if (this.client.options.getInvertYMouse().getValue()) {
                    q = -1;
                }

                this.client.getTutorialManager().onUpdateMouse(o, p);
                if (this.client.player != null) {
                    this.client.player.changeLookDirection(o, p * (double)q);
                }
            }
        }
        else {
            this.cursorDeltaX = 0.0D;
            this.cursorDeltaY = 0.0D;
        }
    }
}