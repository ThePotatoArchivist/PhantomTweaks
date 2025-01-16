package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.Stunnable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityStunMixin extends FlyingEntity implements Stunnable {
	@Unique
	private int phantomstun$stunnedTicks = 0;

	protected PhantomEntityStunMixin(EntityType<? extends FlyingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
			method = "writeCustomDataToNbt",
			at = @At("TAIL")
	)
	private void writeStun(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt("Stunned", phantomstun$stunnedTicks);
	}

	@Inject(
			method = "readCustomDataFromNbt",
			at = @At("TAIL")
	)
	private void readStun(NbtCompound nbt, CallbackInfo ci) {
		phantomstun$stunnedTicks = nbt.getInt("Stunned");
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (!super.damage(source, amount)) return false;

		// Players are handled separately
		if ((source.getSource() instanceof LivingEntity livingEntity && !(source.getAttacker() instanceof PlayerEntity) && livingEntity.disablesShield()) || source.getSource() instanceof PersistentProjectileEntity projectile && projectile.isCritical()) {
			phantomstun$setStunned();
		}

		return true;
	}

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void tickStun(CallbackInfo ci) {
        if (phantomstun$stunnedTicks > 0) {
            phantomstun$stunnedTicks--;
		}
	}

	@Override
	public boolean phantomstun$isStunned() {
		return phantomstun$stunnedTicks > 0;
	}

	@Override
	public void phantomstun$setStunned() {
		phantomstun$stunnedTicks = 120;
		setTarget(null);
	}
}
