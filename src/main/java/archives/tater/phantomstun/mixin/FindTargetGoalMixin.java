package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.world.entity.monster.Phantom;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomAttackPlayerTargetGoal")
public class FindTargetGoalMixin {
    @Shadow @Final Phantom field_7319;

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true)
    private void checkStun(CallbackInfoReturnable<Boolean> cir) {
        if (((Stunnable) field_7319).phantomstun$isStunned()) cir.setReturnValue(false);
    }
}
