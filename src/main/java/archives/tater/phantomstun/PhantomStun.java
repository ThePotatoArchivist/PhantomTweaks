package archives.tater.phantomstun;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomStun implements ModInitializer {
	public static final String MOD_ID = "phantomstun";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final PhantomStunConfig CONFIG = PhantomStunConfig.createToml(
			FabricLoader.getInstance().getConfigDir(),
			"",
			MOD_ID,
			PhantomStunConfig.class
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}
}
