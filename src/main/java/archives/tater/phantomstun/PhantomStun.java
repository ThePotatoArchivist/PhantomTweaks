package archives.tater.phantomstun;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomStun implements ModInitializer {
	public static final String MOD_ID = "phantomstun";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final TagKey<DamageType> MELEE_STUN_TAG = TagKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MOD_ID, "melee_stun"));
	public static final TagKey<EntityType<?>> ALWAYS_STUN_TAG = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(MOD_ID, "always_stun"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		MidnightConfig.init(MOD_ID, PhantomStunConfig.class);
	}
}
