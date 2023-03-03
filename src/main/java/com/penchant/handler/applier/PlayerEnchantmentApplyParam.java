package com.penchant.handler.applier;

import com.penchant.domain.enumeration.PlayerEnchantment;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEnchantmentApplyParam {
    private ServerPlayerEntity player;
    private PlayerEnchantment enchantment;
    private int enchantmentIndex;
    private boolean sendMessage;

    public PlayerEnchantmentApplyParam(ServerPlayerEntity player, PlayerEnchantment enchantment, int enchantmentIndex, boolean sendMessage) {
        this.player = player;
        this.enchantment = enchantment;
        this.enchantmentIndex = enchantmentIndex;
        this.sendMessage = sendMessage;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public PlayerEnchantment getEnchantment() {
        return enchantment;
    }

    public boolean sendMessage() {
        return sendMessage;
    }

    public int getEnchantmentIndex() {
        return enchantmentIndex;
    }
}
