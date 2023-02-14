package com.penchant.handler;

import com.google.common.collect.Lists;
import com.penchant.PlayerEnchantMod;
import com.penchant.screen.PlayerEnchanterScreen;
import com.penchant.screen.PlayerEnchanterScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;
import java.util.Objects;

public class PlayerEnchantButtonHandler {
    private final PlayerEnchanterScreen playerEnchanterScreen;
    private MinecraftClient client;

    public PlayerEnchantButtonHandler(PlayerEnchanterScreen playerEnchanterScreen, MinecraftClient client) {
        this.playerEnchanterScreen = playerEnchanterScreen;
    }

    public void setClient(MinecraftClient client) {
        this.client = client;
    }

    public void onClick(ButtonWidget button) {
        List<String> validationResults = validateAndGetResults();

        if (!validationResults.isEmpty()) {
            for (String result : validationResults) {
                PlayerEnchantMod.LOGGER.info(result);
            }

            return;
        }

        PlayerEnchantMod.LOGGER.info("강화 가능!!");

        decrementExperienceLevel();
        destroyItem();
        applyEnchantment();
    }

    private void decrementExperienceLevel() {
        // TODO
    }

    private void destroyItem() {
        // TODO
    }

    private void applyEnchantment() {
        // TODO
    }

    private List<String> validateAndGetResults() {
        List<String> result = Lists.newArrayList();

        if (this.client == null) {
            result.add("[PlayerEnchantButtonHandler] this.client is null");
            return result;
        }

        if (this.client.player == null) {
            result.add("[PlayerEnchantButtonHandler] this.client.player is null");
            return result;
        }

        int playerExperienceLevel = this.client.player.experienceLevel;

        if (insufficientExperienceLevel(playerExperienceLevel)) {
            result.add("[PlayerEnchantButtonHandler] 레벨 부족함. 플레이어 레벨: %s, 비용: %s"
                    .formatted(playerExperienceLevel, PlayerEnchanterScreenHandler.LEVEL_COST));
        }

        String itemOnSlot = getItemOnSlot();

        if (!Objects.equals(itemOnSlot, "item.penchant.tanzanite")) {
            result.add("[PlayerEnchantButtonHandler] 올려진 아이템이 탄자나이트가 아님. item: %s".formatted(itemOnSlot));
        }

        return result;
    }

    private String getItemOnSlot() {
        return this.playerEnchanterScreen.getScreenHandler().slots.get(0).getStack().getItem().getTranslationKey();
    }

    private boolean insufficientExperienceLevel(int playerExperienceLevel) {
        return playerExperienceLevel < PlayerEnchanterScreenHandler.LEVEL_COST;
    }
}
