package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static java.lang.Math.max;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerCountMixin {
    @ModifyExpressionValue(
            method = "spawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Difficulty;getId()I")
    )
    private int decreaseSpawnCount(int original) {
        return max(original - 2, 0);
    }
}
