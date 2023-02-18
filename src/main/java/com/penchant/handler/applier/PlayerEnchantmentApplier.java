package com.penchant.handler.applier;

import com.penchant.PlayerEnchantMod;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerEnchantmentApplier {

    public static void applyWalkFaster(PlayerEnchantmentApplyParam param) {
        param.getPlayer()
                .getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                .setBaseValue(param.getPlayer().getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue() + param.getEnchantment().getValue());

        log(param);
    }

    public static void applyReinforceDamage(PlayerEnchantmentApplyParam param) {
        param.getPlayer()
                .getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                .setBaseValue(param.getPlayer().getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue() + param.getEnchantment().getValue());

        log(param);
    }

    public static void applyReinforceArmor(PlayerEnchantmentApplyParam param) {
        param.getPlayer()
                .getAttributeInstance(EntityAttributes.GENERIC_ARMOR)
                .setBaseValue(param.getPlayer().getAttributeInstance(EntityAttributes.GENERIC_ARMOR).getBaseValue() + param.getEnchantment().getValue());

        log(param);
    }

    public static void applyReinforceArmorToughness(PlayerEnchantmentApplyParam param) {
        param.getPlayer()
                .getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)
                .setBaseValue(param.getPlayer().getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getBaseValue() + param.getEnchantment().getValue());

        log(param);
    }

    public static void applyReinforceLuck(PlayerEnchantmentApplyParam param) {
        param.getPlayer()
                .getAttributeInstance(EntityAttributes.GENERIC_LUCK)
                .setBaseValue(param.getPlayer().getAttributeInstance(EntityAttributes.GENERIC_LUCK).getBaseValue() + param.getEnchantment().getValue());

        log(param);
    }

    public static void applyIncreaseMaxHealth(PlayerEnchantmentApplyParam param) {
        param.getPlayer()
                .getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
                .setBaseValue(param.getPlayer().getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue() + param.getEnchantment().getValue());

        log(param);
    }

    private static void log(PlayerEnchantmentApplyParam param) {
        if (!param.doLog()) {
            return;
        }

        PlayerEnchantMod.LOGGER.info("player: {}, {} 능력이 {}만큼 추가되었습니다. 중첩된 횟수: {}",
                param.getPlayer().getName(), param.getEnchantment(), param.getEnchantment().getValue(), param.getOverlappedCount() + 1);

        for (ServerPlayerEntity player : PlayerEnchantMod.SERVER.getPlayerManager().getPlayerList()) {
            player.sendMessage(Text.translatable("penchant.player_enchanted",
                    param.getPlayer().getName(), param.getEnchantment().getLabel(), param.getOverlappedCount() + 1).formatted(Formatting.DARK_PURPLE));
        }
    }
}
