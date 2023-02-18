package com.penchant.handler;

import com.google.common.collect.Lists;
import com.penchant.PlayerEnchantMod;
import com.penchant.screen.PlayerEnchanterScreenHandler;
import com.penchant.util.StaticPolicy;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public class PlayerEnchantButtonHandler {

    public static void handleEnchantButtonClicked(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        List<String> validationResults = validateAndGetResults(player);

        if (!validationResults.isEmpty()) {
            for (String result : validationResults) {
                PlayerEnchantMod.LOGGER.info(result);
            }

            return;
        }

        if (PlayerEnchantmentApplyHandler.playerEnchantable(player.getName().toString())) {
            destroyItem(player);
            applyEnchantmentCost(player);
            applyRandomEnchantment(player);
        } else {
            player.sendMessage(Text.translatable("penchant.no_more_enchantment_available").formatted(Formatting.DARK_RED));
            PlayerEnchantMod.LOGGER.info("[PlayerAbilityApplyHandler] playerId: {}, playerName: {}, 더이상 인챈트 불가능함.", player.getId(), player.getName());
        }
    }

    private static List<String> validateAndGetResults(ServerPlayerEntity player) {
        List<String> result = Lists.newArrayList();

        int playerExperienceLevel = player.experienceLevel;

        if (insufficientExperienceLevel(playerExperienceLevel)) {
            result.add("[PlayerEnchantButtonHandler] 레벨 부족함. 플레이어 레벨: %s, 비용: %s"
                    .formatted(playerExperienceLevel, StaticPolicy.LEVEL_COST));
        }

        String itemOnSlot = getItemOnSlot(player);

        if (!Objects.equals(itemOnSlot, "item.penchant.tanzanite")) {
            result.add("[PlayerEnchantButtonHandler] 올려진 아이템이 탄자나이트가 아님. item: %s".formatted(itemOnSlot));
        }

        return result;
    }

    private static String getItemOnSlot(ServerPlayerEntity player) {
        return player.currentScreenHandler.slots.get(0).getStack().getItem().getTranslationKey();
    }

    private static boolean insufficientExperienceLevel(int playerExperienceLevel) {
        return playerExperienceLevel < StaticPolicy.LEVEL_COST;
    }

    private static void destroyItem(ServerPlayerEntity player) {
        ((PlayerEnchanterScreenHandler) player.currentScreenHandler).getInventory().setStack(0, ItemStack.EMPTY);
    }

    private static void applyEnchantmentCost(ServerPlayerEntity player) {
        player.applyEnchantmentCosts(player.playerScreenHandler.getSlot(0).getStack(), StaticPolicy.LEVEL_COST);
    }

    private static void applyRandomEnchantment(ServerPlayerEntity player) {
        PlayerEnchantmentApplyHandler.applyRandomEnchantment(player);
    }
}
