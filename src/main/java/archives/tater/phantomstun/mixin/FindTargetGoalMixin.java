package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public class FindTargetGoalMixin {
    @Shadow @Final PhantomEntity field_7319;

    @Inject(
            method = "canStart",
            at = @At("HEAD"),
            cancellable = true)
    private void checkStun(CallbackInfoReturnable<Boolean> cir) {
        if (((Stunnable) field_7319).phantomstun$isStunned()) cir.setReturnValue(false);
    }
}
