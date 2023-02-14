package com.penchant.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.penchant.handler.PlayerEnchantButtonHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlayerEnchanterScreen extends HandledScreen<PlayerEnchanterScreenHandler> {
    private static final int GREEN = 8453920;
    private static final int RED = 16736352;

    private static final Identifier SCREEN_TEXTURE = new Identifier("penchant", "textures/screen/player_enchanter.png");
    private static final Identifier CHECK_BUTTON_TEXTURE = new Identifier("penchant", "textures/screen/check_button.png");
    private final PlayerEnchantButtonHandler playerEnchantButtonHandler;
    private final TexturedButtonWidget buttonWidget;

    public PlayerEnchanterScreen(PlayerEnchanterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.playerEnchantButtonHandler = new PlayerEnchantButtonHandler(this, this.client);
        this.buttonWidget = new TexturedButtonWidget(221, 71, 20, 18, 0, 0, 19, CHECK_BUTTON_TEXTURE, playerEnchantButtonHandler::onClick);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SCREEN_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.addDrawableChild(buttonWidget);
        playerEnchantButtonHandler.setClient(this.client);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
        Text text = Text.translatable("screen.penchant.player_enchanter.level_cost", PlayerEnchanterScreenHandler.LEVEL_COST);

        if (this.client.player.experienceLevel >=  PlayerEnchanterScreenHandler.LEVEL_COST) {
            this.textRenderer.drawWithShadow(matrices, text, 78, 65, GREEN);
        } else {
            this.textRenderer.drawWithShadow(matrices, text, 78, 65, RED);
        }
    }
}