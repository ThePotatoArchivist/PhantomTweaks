package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.world.entity.monster.Phantom;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomMoveControl")
public class PhantomMoveControlMixin {

    @Shadow @Final Phantom field_7330;

    @Inject(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Phantom;getYaw()F"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Phantom;setPitch(F)V")
            ),
            cancellable = true)
    private void checkStun(CallbackInfo ci) {
        if (((Stunnable) field_7330).phantomstun$isStunned()) {
            field_7330.setDeltaMovement(field_7330.getDeltaMovement().add(0.0, -0.02, 0.0));
            ci.cancel();
        }
    }
}
