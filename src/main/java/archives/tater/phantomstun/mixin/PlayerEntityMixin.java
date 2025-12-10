package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("ConstantValue")
    @WrapOperation(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addAttackParticlesAndSounds(Lnet/minecraft/entity/Entity;ZZZZF)V")
    )
    private void stunPhantomCrit(PlayerEntity instance, Entity target, boolean criticalHit, boolean sweeping, boolean cooldownPassed, boolean pierce, float enchantDamage, Operation<Void> original) {
        original.call(instance, target, criticalHit, sweeping, cooldownPassed, pierce, enchantDamage);

        if (!criticalHit && (!cooldownPassed || getWeaponDisableBlockingForSeconds() <= 0)) return;
        if (!(target instanceof PhantomEntity || !((Object) this instanceof ServerPlayerEntity) || ((Stunnable) target).phantomstun$isStunned())) return;

        ((Stunnable) target).phantomstun$setStunned();
    }
}
