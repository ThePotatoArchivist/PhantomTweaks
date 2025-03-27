package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.PhantomSpawner;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PhantomSpawner.class)
@Debug(export = true)
public class PhantomSpawnerTimingMixin {
    @Shadow private int cooldown;

    @WrapOperation(
            method = "spawn",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/spawner/PhantomSpawner;cooldown:I", opcode = Opcodes.PUTFIELD),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0)
            )
    )
    private void changeCooldown(PhantomSpawner instance, int value, Operation<Void> original) {
        original.call(instance, cooldown + 15 * 20);
    }

    @ModifyReturnValue(
            method = "spawn",
            at = @At("TAIL")
    )
    private int setCooldown(int original, @Local(argsOnly = true) ServerWorld world) {
        // No more phantoms for 3-5 minutes
        if (original > 0)
            cooldown = cooldown + (180 + world.random.nextInt(120)) * 20;
        return original;
    }
}
