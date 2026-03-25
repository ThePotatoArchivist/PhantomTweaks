package archives.tater.phantomstun.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.levelgen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerHealthMixin {
    @Definition(id = "nextInt", method = "Lnet/minecraft/util/RandomSource;nextInt(I)I")
    @Definition(id = "value", local = @Local(type = int.class, name = "value"))
    @Expression("?.nextInt(value)")
    @ModifyExpressionValue(
            method = "tick",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private int checkHealth(int original, @Local(name = "player") ServerPlayer player) {
        return player.getHealth() < player.getAttributeBaseValue(Attributes.MAX_HEALTH) / 2f ? 72000 : 0;
   }
}
