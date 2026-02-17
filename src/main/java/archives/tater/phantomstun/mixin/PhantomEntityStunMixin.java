package archives.tater.phantomstun.mixin;

import archives.tater.phantomstun.PhantomStun;
import archives.tater.phantomstun.Stunnable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Phantom.class)
public abstract class PhantomEntityStunMixin extends Mob implements Stunnable {
	@Unique
	private int phantomstun$stunnedTicks = 0;

	protected PhantomEntityStunMixin(EntityType<? extends Mob> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
			method = "addAdditionalSaveData",
			at = @At("TAIL")
	)
	private void writeStun(ValueOutput view, CallbackInfo ci) {
		view.putInt("Stunned", phantomstun$stunnedTicks);
	}

	@Inject(
			method = "readAdditionalSaveData",
			at = @At("TAIL")
	)
	private void readStun(ValueInput view, CallbackInfo ci) {
		phantomstun$stunnedTicks = view.getIntOr("Stunned", 0);
	}

	@Override
	public boolean hurtServer(ServerLevel serverWorld, DamageSource source, float amount) {
		if (!super.hurtServer(serverWorld, source, amount)) return false;

		var sourceEntity = source.getDirectEntity();
		if (source.is(PhantomStun.ALWAYS_STUN_DAMAGE_TAG)
				|| (sourceEntity != null && sourceEntity.getType().is(PhantomStun.ALWAYS_STUN_ENTITY_TAG))
				// Players are handled separately
				|| (sourceEntity instanceof LivingEntity livingEntity && !(source.getEntity() instanceof Player) && source.is(PhantomStun.MELEE_STUN_TAG) && livingEntity.getSecondsToDisableBlocking() > 0)
				|| (sourceEntity instanceof AbstractArrow projectile && projectile.isCritArrow())) {
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
