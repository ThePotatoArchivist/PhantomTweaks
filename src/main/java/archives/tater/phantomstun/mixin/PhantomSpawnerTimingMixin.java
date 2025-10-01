package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.objectweb.asm.Opcodes;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.PhantomSpawner;

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

    @Inject(
            method = "spawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/LocalDifficulty;getGlobalDifficulty()Lnet/minecraft/world/Difficulty;")
    )
    private void setCooldown(ServerWorld world, boolean spawnMonsters, CallbackInfo ci) {
        // No more phantoms for 3-5 minutes
        if (cooldown < 180 * 20)
            cooldown = cooldown + (180 + world.random.nextInt(120)) * 20;
    }
}
