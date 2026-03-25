package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.monster.Phantom;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomAttackPlayerTargetGoal")
public class FindTargetGoalMixin {
    @Shadow @Final Phantom this$0;

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true)
    private void checkStun(CallbackInfoReturnable<Boolean> cir) {
        if (((Stunnable) this$0).phantomstun$isStunned()) cir.setReturnValue(false);
    }
}
