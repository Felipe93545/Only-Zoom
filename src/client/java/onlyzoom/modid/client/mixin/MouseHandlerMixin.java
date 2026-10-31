package onlyzoom.modid.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import onlyzoom.modid.client.ZoomHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
	@Shadow private double accumulatedDX;
	@Shadow private double accumulatedDY;

	@Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
	private void onlyzoom$scroll(long handle, double xoffset, double yoffset, CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();
		if (ZoomHandler.zooming && mc.gui.screen() == null && mc.gui.overlay() == null) {
			ZoomHandler.adjust(yoffset);
			ci.cancel();
		}
	}

	@Inject(method = "turnPlayer", at = @At("HEAD"))
	private void onlyzoom$slowTurn(double mousea, CallbackInfo ci) {
		double scale = ZoomHandler.getCurrent();
		if (scale < 1.0) {
			this.accumulatedDX *= scale;
			this.accumulatedDY *= scale;
		}
	}
}