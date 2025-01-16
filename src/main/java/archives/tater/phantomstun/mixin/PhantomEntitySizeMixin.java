package archives.tater.phantomstun.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.util.Objects.requireNonNull;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntitySizeMixin extends LivingEntity {
    @Shadow public abstract int getPhantomSize();

    protected PhantomEntitySizeMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArg(
            method = "initialize",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;setPhantomSize(I)V")
    )
    private int defaultSize(int size) {
        return 1;
    }

    @Inject(
            method = "initialize",
            at = @At("TAIL")
    )
    private void heal(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        setHealth(getMaxHealth());
    }

    @Inject(
            method = "onSizeChanged",
            at = @At("TAIL")
    )
    private void setMaxHealth(CallbackInfo ci) {
        requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20 + 20 * getPhantomSize());
    }
}
