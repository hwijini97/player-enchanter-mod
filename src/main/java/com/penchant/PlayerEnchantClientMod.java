package com.penchant;

import com.penchant.screen.PlayerEnchanterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class PlayerEnchantClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(PlayerEnchantMod.BOX_SCREEN_HANDLER, PlayerEnchanterScreen::new);
    }
}