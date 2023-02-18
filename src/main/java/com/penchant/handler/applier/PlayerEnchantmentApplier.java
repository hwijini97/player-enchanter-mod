package com.penchant.handler.applier;

import com.penchant.PlayerEnchantMod;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class PlayerEnchantmentApplier {

    public static void applyWalkFaster(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }

    public static void applyReinforceDamage(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    public static void applyReinforceArmor(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_ARMOR);
    }

    public static void applyReinforceArmorToughness(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
    }

    public static void applyReinforceLuck(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_LUCK);
    }

    public static void applyIncreaseMaxHealth(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_MAX_HEALTH);
    }

    private static void applyEnchantment(PlayerEnchantmentApplyParam param, EntityAttribute genericMaxHealth) {
        Objects.requireNonNull(param.getPlayer().getAttributeInstance(genericMaxHealth))
                .setBaseValue(param.getEnchantment().getValues().get(param.getEnchantmentIndex()));

        log(param);
    }

    private static void log(PlayerEnchantmentApplyParam param) {
        if (!param.doLog()) {
            return;
        }

        PlayerEnchantMod.LOGGER.info("player: {}, {} 능력 {}단계가 추가되었습니다.",
                param.getPlayer().getName(), param.getEnchantment(), param.getEnchantmentIndex() + 1);

        for (ServerPlayerEntity player : PlayerEnchantMod.SERVER.getPlayerManager().getPlayerList()) {
            player.sendMessage(Text.translatable("penchant.player_enchanted",
                    param.getPlayer().getName(), param.getEnchantment().getLabel(), param.getEnchantmentIndex() + 1).formatted(Formatting.DARK_PURPLE));
        }
    }
}
