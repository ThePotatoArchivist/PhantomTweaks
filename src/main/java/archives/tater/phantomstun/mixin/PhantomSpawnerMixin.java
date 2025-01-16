package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
    @ModifyExpressionValue(
            method = "spawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/stat/Stats;TIME_SINCE_REST:Lnet/minecraft/util/Identifier;")
            )
    )
    private int checkHealth(int original, @Local(argsOnly = true) ServerWorld world, @Local ServerPlayerEntity player) {
        return world.random.nextInt(2) == 0 && player.getHealth() < player.defaultMaxHealth / 2f ? 72000 : 0;
    }
}
