package com.penchant.handler.applier;

import com.penchant.domain.enumeration.PlayerEnchantment;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEnchantmentApplyParam {
    private ServerPlayerEntity player;
    private PlayerEnchantment enchantment;
    private int enchantmentIndex;
    private boolean doLog;

    public PlayerEnchantmentApplyParam(ServerPlayerEntity player, PlayerEnchantment enchantment, int enchantmentIndex, boolean doLog) {
        this.player = player;
        this.enchantment = enchantment;
        this.enchantmentIndex = enchantmentIndex;
        this.doLog = doLog;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public PlayerEnchantment getEnchantment() {
        return enchantment;
    }

    public boolean doLog() {
        return doLog;
    }

    public int getEnchantmentIndex() {
        return enchantmentIndex;
    }
}
