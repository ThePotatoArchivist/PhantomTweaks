package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$PhantomMoveControl")
public class PhantomMoveControlMixin {

    @Shadow @Final PhantomEntity field_7330;

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true)
    private void checkStun(CallbackInfo ci) {
        if (((Stunnable) field_7330).phantomstun$isStunned()) ci.cancel();
    }
}
