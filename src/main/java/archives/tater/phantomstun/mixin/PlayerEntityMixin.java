package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @SuppressWarnings("ConstantValue")
    @WrapOperation(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;attackVisualEffects(Lnet/minecraft/world/entity/Entity;ZZZZF)V")
    )
    private void stunPhantomCrit(Player instance, Entity target, boolean criticalHit, boolean sweeping, boolean cooldownPassed, boolean pierce, float enchantDamage, Operation<Void> original) {
        original.call(instance, target, criticalHit, sweeping, cooldownPassed, pierce, enchantDamage);

        if (!criticalHit && (!cooldownPassed || getSecondsToDisableBlocking() <= 0)) return;
        if (!(target instanceof Phantom || !((Object) this instanceof ServerPlayer) || ((Stunnable) target).phantomstun$isStunned())) return;

        ((Stunnable) target).phantomstun$setStunned();
    }
}
