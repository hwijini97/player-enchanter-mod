package com.penchant.screen;

import com.penchant.PlayerEnchantMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EnchantButtonWidget extends TexturedButtonWidget {

    public EnchantButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, ButtonWidget.PressAction pressAction) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, pressAction);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEnchanterScreen screen = (PlayerEnchanterScreen) client.currentScreen;
        assert screen != null;

        int screenX = (int) (client.currentScreen.width * 0.55f);
        int screenY = (int) (client.currentScreen.height * 0.335f);

        setX(screenX - getWidth() / 2);
        setY(screenY - getHeight() / 2);

        PlayerEnchantMod.LOGGER.info("{}, {}, {}, {}", screen.width, screen.height, screen.getBackgroundWidth(), screen.getBackgroundHeight());

        super.renderButton(matrices, mouseX, mouseY, delta);
    }
}
