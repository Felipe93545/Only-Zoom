package onlyzoom.modid.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class OnlyZoomClient implements ClientModInitializer {
	public static KeyMapping zoomKey;

	@Override
	public void onInitializeClient() {
		KeyMapping.Category category = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath("onlyzoom", "main"));

				zoomKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.onlyzoom.zoom",
			InputConstants.KEY_C,
			category
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ZoomHandler.zooming = zoomKey.isDown();
		});
	}
}