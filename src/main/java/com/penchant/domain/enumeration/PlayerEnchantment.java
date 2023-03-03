package com.penchant.domain.enumeration;

import com.penchant.handler.applier.PlayerEnchantmentApplier;
import com.penchant.handler.applier.PlayerEnchantmentApplyParam;

import java.util.List;
import java.util.function.Consumer;

public enum PlayerEnchantment {
    WALK_FASTER("이동속도 증가", List.of(0.12, 0.13, 0.14), PlayerEnchantmentApplier::applyWalkFaster),
    REINFORCE_DAMAGE("근접 공격력 강화", List.of(4.0, 6.0, 8.0), PlayerEnchantmentApplier::applyReinforceDamage),
    REINFORCE_ARMOR("방어력 강화", List.of(2.0, 4.0, 6.0), PlayerEnchantmentApplier::applyReinforceArmor),
    REINFORCE_ARMOR_TOUGHNESS("방어 강도 강화", List.of(2.0, 4.0, 6.0), PlayerEnchantmentApplier::applyReinforceArmorToughness),
    REINFORCE_KNOCK_BACK_RESISTANCE("밀치기 저항 강화", List.of(0.2, 0.3, 0.4), PlayerEnchantmentApplier::applyKnockBackResistance),
    REINFORCE_LUCK("바다의 행운 강화", List.of(15.0, 24.0, 32.0), PlayerEnchantmentApplier::applyReinforceLuck),
    INCREASE_MAX_HEALTH("최대 체력 증가", List.of(28.0, 36.0, 40.0), PlayerEnchantmentApplier::applyIncreaseMaxHealth),
    ;

    private final String label;
    private final List<Double> values;
    private final Consumer<PlayerEnchantmentApplyParam> applier;

    PlayerEnchantment(String label, List<Double> values, Consumer<PlayerEnchantmentApplyParam> applier) {
        this.label = label;
        this.values = values;
        this.applier = applier;
    }

    public List<Double> getValues() {
        return values;
    }

    public Consumer<PlayerEnchantmentApplyParam> getApplier() {
        return applier;
    }

    public String getLabel() {
        return label;
    }
}
