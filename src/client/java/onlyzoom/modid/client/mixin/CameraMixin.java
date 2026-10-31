package onlyzoom.modid.client.mixin;

import net.minecraft.client.Camera;
import onlyzoom.modid.client.ZoomHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {
	@Inject(method = "calculateFov", at = @At("RETURN"), cancellable = true)
	private void onlyzoom$applyZoom(float partialTicks, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(cir.getReturnValue() * (float) ZoomHandler.getMultiplier());
	}
}