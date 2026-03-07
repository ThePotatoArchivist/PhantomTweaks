package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(
            method = "stabAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostAttackEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V")
    )
    private void kineticStun(EquipmentSlot slot, Entity entity, float damageAmount, boolean damage, boolean knockback, boolean dismount, CallbackInfoReturnable<Boolean> cir) {
        if (knockback && entity instanceof Stunnable stunnable && !stunnable.phantomstun$isStunned())
            stunnable.phantomstun$setStunned();
    }
}
