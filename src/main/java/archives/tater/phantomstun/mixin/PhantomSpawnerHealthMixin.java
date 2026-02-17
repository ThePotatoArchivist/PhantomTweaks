package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import org.objectweb.asm.Opcodes;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerHealthMixin {
    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/stats/Stats;TIME_SINCE_REST:Lnet/minecraft/resources/Identifier;", opcode = Opcodes.GETSTATIC)
            )
    )
    private int checkHealth(int original, @Local(argsOnly = true) ServerLevel world, @Local ServerPlayer player) {
        return player.getHealth() < player.getAttributeBaseValue(Attributes.MAX_HEALTH) / 2f ? 72000 : 0;
   }
}
