package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$PhantomMoveControl")
public class PhantomMoveControlMixin {

    @Shadow @Final PhantomEntity field_7330;

    @Inject(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;getYaw()F"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;setPitch(F)V")
            ),
            cancellable = true)
    private void checkStun(CallbackInfo ci) {
        if (((Stunnable) field_7330).phantomstun$isStunned()) {
            field_7330.setVelocity(field_7330.getVelocity().add(0.0, -0.02, 0.0));
            ci.cancel();
        }
    }
}
