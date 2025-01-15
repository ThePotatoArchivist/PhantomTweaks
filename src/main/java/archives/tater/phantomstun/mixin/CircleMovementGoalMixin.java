package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$CircleMovementGoal")
public class CircleMovementGoalMixin {

    @Shadow @Final PhantomEntity field_7325;

    @Inject(
            method = "canStart",
            at = @At("HEAD"),
            cancellable = true)
    private void checkStun(CallbackInfoReturnable<Boolean> cir) {
        if (((Stunnable) field_7325).phantomstun$isStunned()) cir.setReturnValue(false);
    }
}
