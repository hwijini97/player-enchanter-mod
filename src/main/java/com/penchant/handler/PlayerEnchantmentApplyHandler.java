package com.penchant.handler;

import com.google.common.collect.Lists;
import com.penchant.PlayerEnchantMod;
import com.penchant.domain.enumeration.PlayerEnchantment;
import com.penchant.handler.applier.PlayerEnchantmentApplyParam;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlayerEnchantmentApplyHandler {

    public static void applyRandomEnchantment(ServerPlayerEntity player) {
        List<Pair<PlayerEnchantment, Integer>> availableEnchantment = getAvailableEnchantment(player.getName().getString());

        if (availableEnchantment.isEmpty()) {
            PlayerEnchantMod.LOGGER.info("[PlayerAbilityApplyHandler] playerId: {}, playerName: {}, 더이상 인챈트 불가능함. 여기로 진입하면 안되는데?", player.getId(), player.getName());
            return;
        }

        Pair<PlayerEnchantment, Integer> enchantment = availableEnchantment.get(new Random().nextInt(0, availableEnchantment.size()));
        enchantment.getLeft().getApplier().accept(new PlayerEnchantmentApplyParam(player, enchantment.getLeft(), enchantment.getRight(), true));
        PlayerEnchantmentFileHandler.addEnchantment(player.getName().getString(), enchantment.getLeft(), enchantment.getRight());

        PlayerEnchantMod.LOGGER.info("player: {}, {} 능력이 적용되었습니다. 인챈트 단계: {}", player.getName(), enchantment.getLeft(), enchantment.getRight() + 1);
    }

    public static boolean playerEnchantable(String playerName) {
        return !getAvailableEnchantment(playerName).isEmpty();
    }

    private static List<Pair<PlayerEnchantment, Integer>> getAvailableEnchantment(String playerName) {
        Map<PlayerEnchantment, Integer> playerEnchantmentInfo = PlayerEnchantmentFileHandler.getPlayerEnchantmentInfo(playerName);
        List<Pair<PlayerEnchantment, Integer>> result = Lists.newArrayList();

        for (PlayerEnchantment enchantment : PlayerEnchantment.values()) {
            if (playerEnchantmentInfo.containsKey(enchantment)) {

                for (int i = 0; i < enchantment.getValues().size(); i++) {
                    if (playerEnchantmentInfo.get(enchantment) < i) {
                        result.add(new Pair<>(enchantment, i));
                    }
                }
            } else {
                for (int i = 0; i < enchantment.getValues().size(); i++) {
                    result.add(new Pair<>(enchantment, i));
                }
            }
        }

        return result;
    }
}
