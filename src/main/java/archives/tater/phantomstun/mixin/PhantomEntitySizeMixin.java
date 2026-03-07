package archives.tater.phantomstun.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.util.Objects.requireNonNull;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(Phantom.class)
public abstract class PhantomEntitySizeMixin extends LivingEntity {
    @Shadow public abstract int getPhantomSize();

    protected PhantomEntitySizeMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyArg(
            method = "finalizeSpawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Phantom;setPhantomSize(I)V")
    )
    private int defaultSize(int size) {
        return 1;
    }

    @Inject(
            method = "finalizeSpawn",
            at = @At("TAIL")
    )
    private void heal(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData entityData, CallbackInfoReturnable<SpawnGroupData> cir) {
        setHealth(getMaxHealth());
    }

    @Inject(
            method = "updatePhantomSizeInfo",
            at = @At("TAIL")
    )
    private void setMaxHealth(CallbackInfo ci) {
        requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20 + 20 * getPhantomSize());
    }
}
