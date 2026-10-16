package pl.knockbackdisplacement.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import pl.knockbackdisplacement.KnockbackConfig;

public class ConfigScreen extends Screen {
	private ButtonWidget toggleButton;

	public ConfigScreen() {
		super(Text.literal("Knockback Displacement"));
	}

	@Override
	protected void init() {
		KnockbackConfig cfg = KnockbackConfig.get();
		int w = 220;
		int x = this.width / 2 - w / 2;
		int y = this.height / 2 - 60;

		toggleButton = ButtonWidget.builder(toggleText(cfg), b -> {
			cfg.enabled = !cfg.enabled;
			b.setMessage(toggleText(cfg));
		}).dimensions(x, y, w, 20).build();
		addDrawableChild(toggleButton);

		addDrawableChild(new ValueSlider(x, y + 28, w, 20, "Moc (bloki na 1.0)",
				0.0, 15.0, 0.1, cfg.blocksPerStrength, v -> cfg.blocksPerStrength = v));

		addDrawableChild(new ValueSlider(x, y + 56, w, 20, "Podniesienie",
				0.0, 3.0, 0.1, cfg.verticalLift, v -> cfg.verticalLift = v));

		addDrawableChild(ButtonWidget.builder(Text.literal("Gotowe"), b -> close())
				.dimensions(x, y + 92, w, 20).build());
	}

	private static Text toggleText(KnockbackConfig cfg) {
		return Text.literal("Mod: " + (cfg.enabled ? "WŁĄCZONY" : "WYŁĄCZONY"));
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 90, 0xFFFFFF);
		context.drawCenteredTextWithShadow(this.textRenderer,
				Text.literal("Vanilla trafienie = siła 0.4, czyli dystans = moc * 0.4"),
				this.width / 2, this.height / 2 + 60, 0xAAAAAA);
	}

	@Override
	public void close() {
		KnockbackConfig.save();
		super.close();
	}
}
