package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V")
    )
    private void stunPhantomCrit(Entity target, CallbackInfo ci) {
        //noinspection ConstantValue
        if (target instanceof PhantomEntity && (Object) this instanceof ServerPlayerEntity && !((Stunnable) target).phantomstun$isStunned()) {
            ((Stunnable) target).phantomstun$setStunned();
        }
    }

    // a bit clunky but this playSound means it was a strong attack (cooldown > 0.9)
    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/Entity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/sound/SoundEvents;ENTITY_PLAYER_ATTACK_STRONG:Lnet/minecraft/sound/SoundEvent;")
            )
    )
    private void stunPhantomAxe(Entity target, CallbackInfo ci) {
        //noinspection ConstantValue
        if (target instanceof PhantomEntity && (Object) this instanceof ServerPlayerEntity && !((Stunnable) target).phantomstun$isStunned() && this.getWeaponDisableBlockingForSeconds() > 0) {
            ((Stunnable) target).phantomstun$setStunned();
        }
    }
}
