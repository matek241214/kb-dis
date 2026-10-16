package pl.knockbackdisplacement.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.knockbackdisplacement.KnockbackConfig;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	/**
	 * Podmienia vanillowy knockback (zmiana velocity) na bezpośrednie przesunięcie pozycji.
	 * Parametry x i z to wektor w stronę atakującego, więc przesuwamy w przeciwną stronę.
	 */
	@Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
	private void knockbackdisplacement$displace(double strength, double x, double z, CallbackInfo ci) {
		KnockbackConfig config = KnockbackConfig.get();
		if (!config.enabled) {
			return;
		}

		LivingEntity self = (LivingEntity) (Object) this;
		boolean isPlayer = self instanceof ServerPlayerEntity;

		if (isPlayer && !config.affectPlayers) {
			return;
		}
		if (!isPlayer && !config.affectMobs) {
			return;
		}

		// Odbieramy vanillowy knockback całkowicie - żadnej velocity.
		ci.cancel();

		if (config.respectKnockbackResistance) {
			strength *= 1.0 - self.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE);
		}
		if (strength <= 0.0) {
			return;
		}

		double lengthSq = x * x + z * z;
		if (lengthSq < 1.0E-8) {
			return;
		}
		double length = Math.sqrt(lengthSq);

		double distance = Math.min(strength * config.blocksPerStrength, config.maxDistance);
		Vec3d offset = new Vec3d(-x / length * distance, config.verticalLift, -z / length * distance);

		// move() respektuje kolizje (ściany, bloki), więc nie wpychamy nikogo w ścianę.
		self.move(MovementType.SELF, offset);

		// Gracz ma pozycję autorytatywną po stronie klienta, więc trzeba mu ją zsynchronizować.
		if (self instanceof ServerPlayerEntity player) {
			player.networkHandler.requestTeleport(
					player.getX(), player.getY(), player.getZ(),
					player.getYaw(), player.getPitch()
			);
		}
	}
}
