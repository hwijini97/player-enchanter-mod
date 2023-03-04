package com.penchant.handler.applier;

import com.penchant.PlayerEnchantMod;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

    public static void applyKnockBackResistance(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
    }

    public static void applyReinforceLuck(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_LUCK);
    }

    public static void applyIncreaseMaxHealth(PlayerEnchantmentApplyParam param) {
        applyEnchantment(param, EntityAttributes.GENERIC_MAX_HEALTH);
    }

    private static void applyEnchantment(PlayerEnchantmentApplyParam param, EntityAttribute entityAttribute) {
        double beforeValue = Objects.requireNonNull(param.getPlayer().getAttributeInstance(entityAttribute)).getBaseValue();

        Objects.requireNonNull(param.getPlayer().getAttributeInstance(entityAttribute))
                .setBaseValue(param.getEnchantment().getValues().get(param.getEnchantmentIndex()));

        double afterValue = Objects.requireNonNull(param.getPlayer().getAttributeInstance(entityAttribute)).getBaseValue();

        PlayerEnchantMod.LOGGER.info("[PlayerEnchantmentApplier] player: {}, enchantment: {}, entityAttribute: {}, before: {}, after: {}",
                param.getPlayer().getName().getString(), param.getEnchantment(), entityAttribute.getTranslationKey(), beforeValue, afterValue);

        handleAfterEnchanted(param);
    }

    private static void handleAfterEnchanted(PlayerEnchantmentApplyParam param) {
        if (param.sendMessage()) {
            sendMessage(param);
        }

        if (!param.getPlayer().getWorld().isClient) {
            playSound(param);
        }
    }

    private static void sendMessage(PlayerEnchantmentApplyParam param) {
        for (ServerPlayerEntity player : PlayerEnchantMod.SERVER.getPlayerManager().getPlayerList()) {
            player.sendMessage(Text.translatable("penchant.player_enchanted",
                    param.getPlayer().getName().getString(), param.getEnchantment().getLabel(), param.getEnchantmentIndex() + 1).formatted(Formatting.DARK_PURPLE));
        }
    }

    private static void playSound(PlayerEnchantmentApplyParam param) {
        param.getPlayer().getWorld().playSound(
                null,
                param.getPlayer().getX(),
                param.getPlayer().getY(),
                param.getPlayer().getZ(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.AMBIENT,
                1.0f,
                1.2f);
    }
}
