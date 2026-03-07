package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @WrapOperation(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;attackVisualEffects(Lnet/minecraft/world/entity/Entity;ZZZZF)V")
    )
    private void stunPhantomCrit(Player instance, Entity target, boolean criticalHit, boolean sweeping, boolean cooldownPassed, boolean pierce, float enchantDamage, Operation<Void> original) {
        original.call(instance, target, criticalHit, sweeping, cooldownPassed, pierce, enchantDamage);

        if (instance.level().isClientSide()) return;
        if (!criticalHit && (!cooldownPassed || getSecondsToDisableBlocking() <= 0)) return;
        if (!(target instanceof Stunnable stunnable) || stunnable.phantomstun$isStunned()) return;

        stunnable.phantomstun$setStunned();
    }

    @Inject(
            method = "stabAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;itemAttackInteraction(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/damagesource/DamageSource;Z)V")
    )
    private void kineticStun(EquipmentSlot slot, Entity entity, float damageAmount, boolean damage, boolean knockback, boolean dismount, CallbackInfoReturnable<Boolean> cir) {
        if (knockback && entity instanceof Stunnable stunnable && !stunnable.phantomstun$isStunned())
            stunnable.phantomstun$setStunned();
    }
}
