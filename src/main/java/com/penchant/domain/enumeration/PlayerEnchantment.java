package com.penchant.domain.enumeration;

import com.penchant.handler.applier.PlayerEnchantmentApplier;
import com.penchant.handler.applier.PlayerEnchantmentApplyParam;

import java.util.function.Consumer;

public enum PlayerEnchantment {
    WALK_FASTER("이동속도 강화", 0.03f, PlayerEnchantmentApplier::applyWalkFaster),
    REINFORCE_DAMAGE("공격력 강화", 3f, PlayerEnchantmentApplier::applyReinforceDamage),
    REINFORCE_ARMOR("방어력 강화", 3f, PlayerEnchantmentApplier::applyReinforceArmor),
    REINFORCE_ARMOR_TOUGHNESS("방어 강도 강화", 3f, PlayerEnchantmentApplier::applyReinforceArmorToughness),
    REINFORCE_LUCK("바다의 행운 강화", 15f, PlayerEnchantmentApplier::applyReinforceLuck),
    INCREASE_MAX_HEALTH("최대 체력 증가", 8f, PlayerEnchantmentApplier::applyIncreaseMaxHealth);

    private final String label;
    private final Float value;
    private final Consumer<PlayerEnchantmentApplyParam> applier;

    PlayerEnchantment(String label, Float value, Consumer<PlayerEnchantmentApplyParam> applier) {
        this.label = label;
        this.value = value;
        this.applier = applier;
    }

    public Float getValue() {
        return value;
    }

    public Consumer<PlayerEnchantmentApplyParam> getApplier() {
        return applier;
    }

    public String getLabel() {
        return label;
    }
}
