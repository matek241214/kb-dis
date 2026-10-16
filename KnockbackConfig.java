package pl.knockbackdisplacement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Prosty config w config/knockback-displacement.json
 */
public class KnockbackConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("knockback-displacement.json");

	/** Włącza/wyłącza cały mod. */
	public boolean enabled = true;

	/** Ile bloków przesunięcia przypada na 1.0 "siły" knockbacku. Vanilla siła = 0.4, więc domyślnie ~1.2 bloku. */
	public double blocksPerStrength = 3.0;

	/** Dodatkowe przesunięcie w górę (w blokach). 0 = tylko poziomo. */
	public double verticalLift = 0.0;

	/** Czy knockback resistance zmniejsza dystans. */
	public boolean respectKnockbackResistance = true;

	/** Czy działa na graczy. */
	public boolean affectPlayers = true;

	/** Czy działa na moby i inne istoty. */
	public boolean affectMobs = true;

	/** Maksymalny dystans pojedynczego przesunięcia w blokach (zabezpieczenie). */
	public double maxDistance = 8.0;

	private static KnockbackConfig instance = new KnockbackConfig();

	public static KnockbackConfig get() {
		return instance;
	}

	public static void load() {
		if (Files.exists(PATH)) {
			try (Reader reader = Files.newBufferedReader(PATH)) {
				KnockbackConfig loaded = GSON.fromJson(reader, KnockbackConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			} catch (Exception e) {
				KnockbackDisplacementMod.LOGGER.error("Nie udało się wczytać configu, używam domyślnego", e);
			}
		}
		save();
	}

	public static void save() {
		try (Writer writer = Files.newBufferedWriter(PATH)) {
			GSON.toJson(instance, writer);
		} catch (IOException e) {
			KnockbackDisplacementMod.LOGGER.error("Nie udało się zapisać configu", e);
		}
	}
}
