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

    public static void handleItemOnSlot(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        List<String> validationResults = validateAndGetResults(player);

        if (!validationResults.isEmpty()) {
            for (String result : validationResults) {
                PlayerEnchantMod.LOGGER.info(result);
            }

            return;
        }

        if (PlayerEnchantmentApplyHandler.playerEnchantable(player.getName().getString())) {
            if (!destroyItem(player)) {
                return;
            }

            applyEnchantmentCost(player);
            applyRandomEnchantment(player);
            player.closeHandledScreen();
        } else {
            player.sendMessage(Text.translatable("penchant.no_more_enchantment_available").formatted(Formatting.DARK_RED));
        }
    }

    private static List<String> validateAndGetResults(ServerPlayerEntity player) {
        List<String> result = Lists.newArrayList();

        validatePlayerExperienceLevel(player, result);
//        validateItemOnSlot(player, result);

        return result;
    }

    private static void validatePlayerExperienceLevel(ServerPlayerEntity player, List<String> result) {
        int playerExperienceLevel = player.experienceLevel;

        if (insufficientExperienceLevel(playerExperienceLevel)) {
            result.add("[PlayerEnchantButtonHandler] 레벨 부족함. 플레이어 레벨: %s, 비용: %s".formatted(playerExperienceLevel, StaticPolicy.LEVEL_COST));
        }
    }

    private static void validateItemOnSlot(ServerPlayerEntity player, List<String> result) {
        String itemOnSlot = getItemOnSlot(player);

        if (!Objects.equals(itemOnSlot, "item.penchant.tanzanite")) {
            result.add("[PlayerEnchantButtonHandler] 올려진 아이템이 탄자나이트가 아님. item: %s".formatted(itemOnSlot));
        }
    }

    private static String getItemOnSlot(ServerPlayerEntity player) {
        return ((PlayerEnchanterScreenHandler) player.currentScreenHandler).getInventory().getStack(0).getItem().getTranslationKey();
    }

    private static String getItemOnCursor(ServerPlayerEntity player) {
        return ((PlayerEnchanterScreenHandler) player.currentScreenHandler).getCursorStack().getItem().getTranslationKey();
    }

    private static boolean insufficientExperienceLevel(int playerExperienceLevel) {
        return playerExperienceLevel < StaticPolicy.LEVEL_COST;
    }

    private static boolean destroyItem(ServerPlayerEntity player) {
        if (Objects.equals(getItemOnSlot(player), "item.penchant.tanzanite")) {
            ((PlayerEnchanterScreenHandler) player.currentScreenHandler).getInventory().setStack(0, ItemStack.EMPTY);
            return true;
        } else if (Objects.equals(getItemOnCursor(player), "item.penchant.tanzanite")) {
            player.currentScreenHandler.setCursorStack(ItemStack.EMPTY);
            return true;
        }

        PlayerEnchantMod.LOGGER.info("[PlayerEnchantButtonHandler] 아이템 파괴 실패. itemOnSlot: {}, itemOnCursor: {}",
                getItemOnSlot(player), getItemOnCursor(player));

        return false;
    }

    private static void applyEnchantmentCost(ServerPlayerEntity player) {
        player.applyEnchantmentCosts(player.playerScreenHandler.getSlot(0).getStack(), StaticPolicy.LEVEL_COST);
    }

    private static void applyRandomEnchantment(ServerPlayerEntity player) {
        PlayerEnchantmentApplyHandler.applyRandomEnchantment(player);
    }
}
