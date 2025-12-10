package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import org.objectweb.asm.Opcodes;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerHealthMixin {
    @ModifyExpressionValue(
            method = "spawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/stat/Stats;TIME_SINCE_REST:Lnet/minecraft/util/Identifier;", opcode = Opcodes.GETSTATIC)
            )
    )
    private int checkHealth(int original, @Local(argsOnly = true) ServerWorld world, @Local ServerPlayerEntity player) {
        return player.getHealth() < player.getAttributeBaseValue(EntityAttributes.MAX_HEALTH) / 2f ? 72000 : 0;
   }
}
