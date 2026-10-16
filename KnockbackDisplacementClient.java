package pl.knockbackdisplacement.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class KnockbackDisplacementClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Wpisz /kbd na czacie, żeby otworzyć GUI.
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
				dispatcher.register(ClientCommandManager.literal("kbd").executes(ctx -> {
					MinecraftClient client = MinecraftClient.getInstance();
					client.execute(() -> client.setScreen(new ConfigScreen()));
					return 1;
				})));
	}
}
