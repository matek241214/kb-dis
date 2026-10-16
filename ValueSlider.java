package pl.knockbackdisplacement.client;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/** Prosty suwak z zakresem min..max i krokiem. */
public class ValueSlider extends SliderWidget {
	private final String label;
	private final double min;
	private final double max;
	private final double step;
	private final Consumer<Double> onChange;

	public ValueSlider(int x, int y, int width, int height, String label,
					   double min, double max, double step, double current, Consumer<Double> onChange) {
		super(x, y, width, height, Text.empty(), (current - min) / (max - min));
		this.label = label;
		this.min = min;
		this.max = max;
		this.step = step;
		this.onChange = onChange;
		updateMessage();
	}

	private double actual() {
		double raw = min + this.value * (max - min);
		return Math.round(raw / step) * step;
	}

	@Override
	protected void updateMessage() {
		setMessage(Text.literal(label + ": " + String.format("%.1f", actual())));
	}

	@Override
	protected void applyValue() {
		onChange.accept(actual());
	}
}
