package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.objectweb.asm.Opcodes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
@Debug(export = true)
public class PhantomSpawnerTimingMixin {
    @Shadow private int nextTick;

    @WrapOperation(
            method = "tick",
            at = @At(value = "FIELD:LAST", target = "Lnet/minecraft/world/level/levelgen/PhantomSpawner;nextTick:I", opcode = Opcodes.PUTFIELD)
    )
    private void changeCooldown(PhantomSpawner instance, int value, Operation<Void> original) {
        original.call(instance, nextTick + 15 * 20);
    }

    @Inject(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/DifficultyInstance;getDifficulty()Lnet/minecraft/world/Difficulty;")
    )
    private void setCooldown(ServerLevel world, boolean spawnMonsters, CallbackInfo ci) {
        // No more phantoms for 3-5 minutes
        if (nextTick < 180 * 20)
            nextTick = nextTick + (180 + world.getRandom().nextInt(120)) * 20;
    }
}
