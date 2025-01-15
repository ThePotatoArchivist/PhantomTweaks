package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlyingEntity.class)
public abstract class FlyingEntityMixin extends MobEntity {
    protected FlyingEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "travel",
            at = @At("HEAD")
    )
    private void phantomsShouldFall(Vec3d movementInput, CallbackInfo ci) {
        //noinspection ConstantValue
        if (!((Object) this instanceof PhantomEntity)) return;
        if (((Stunnable) this).phantomstun$isStunned()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.02, 0.0));
        }
    }
}
