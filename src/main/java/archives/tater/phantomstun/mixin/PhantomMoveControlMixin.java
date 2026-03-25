package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.monster.Phantom;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomMoveControl")
public class PhantomMoveControlMixin {

    @Shadow @Final Phantom this$0;

    @Inject(
            method = "tick",
            at = @At(value = "INVOKE:LAST", target = "Lnet/minecraft/world/entity/monster/Phantom;getYRot()F"),
            cancellable = true)
    private void checkStun(CallbackInfo ci) {
        if (((Stunnable) this$0).phantomstun$isStunned()) {
            this$0.setDeltaMovement(this$0.getDeltaMovement().add(0.0, -0.02, 0.0));
            ci.cancel();
        }
    }
}
