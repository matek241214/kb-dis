package pl.knockbackdisplacement;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnockbackDisplacementMod implements ModInitializer {
	public static final String MOD_ID = "knockback-displacement";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		KnockbackConfig.load();
		LOGGER.info("Knockback Displacement załadowany - knockback przesuwa zamiast nadawać velocity.");
	}
}
