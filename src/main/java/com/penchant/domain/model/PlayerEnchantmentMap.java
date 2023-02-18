package com.penchant.domain.model;

import com.google.common.collect.Maps;
import com.penchant.domain.enumeration.PlayerEnchantment;

import java.util.Map;

public class PlayerEnchantmentMap {
    private final Map<String, Map<PlayerEnchantment, Integer>> playerEnchantmentMap;

    public PlayerEnchantmentMap() {
        this.playerEnchantmentMap = Maps.newHashMap();
    }

    public Map<String, Map<PlayerEnchantment, Integer>> getPlayerEnchantmentMap() {
        return playerEnchantmentMap;
    }
}
