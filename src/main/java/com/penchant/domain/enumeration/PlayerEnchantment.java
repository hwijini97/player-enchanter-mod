package com.penchant.domain.enumeration;

import com.penchant.handler.applier.PlayerEnchantmentApplier;
import com.penchant.handler.applier.PlayerEnchantmentApplyParam;

import java.util.List;
import java.util.function.Consumer;

public enum PlayerEnchantment {
    WALK_FASTER("이동속도 증가", List.of(0.12f, 0.13f, 0.14f), PlayerEnchantmentApplier::applyWalkFaster),
    REINFORCE_DAMAGE("근접 공격력 강화", List.of(4f, 6f, 8f), PlayerEnchantmentApplier::applyReinforceDamage),
    REINFORCE_ARMOR("방어력 강화", List.of(2f, 4f, 6f), PlayerEnchantmentApplier::applyReinforceArmor),
    REINFORCE_ARMOR_TOUGHNESS("방어 강도 강화", List.of(2f, 4f, 6f), PlayerEnchantmentApplier::applyReinforceArmorToughness),
    REINFORCE_KNOCK_BACK_RESISTANCE("밀치기 저항 강화", List.of(0.2f, 0.3f, 0.4f), PlayerEnchantmentApplier::applyKnockBackResistance),
    REINFORCE_LUCK("바다의 행운 강화", List.of(15f, 24f, 32f), PlayerEnchantmentApplier::applyReinforceLuck),
    INCREASE_MAX_HEALTH("최대 체력 증가", List.of(28f, 36f, 40f), PlayerEnchantmentApplier::applyIncreaseMaxHealth),
    ;

    private final String label;
    private final List<Float> values;
    private final Consumer<PlayerEnchantmentApplyParam> applier;

    PlayerEnchantment(String label, List<Float> values, Consumer<PlayerEnchantmentApplyParam> applier) {
        this.label = label;
        this.values = values;
        this.applier = applier;
    }

    public List<Float> getValues() {
        return values;
    }

    public Consumer<PlayerEnchantmentApplyParam> getApplier() {
        return applier;
    }

    public String getLabel() {
        return label;
    }
}
