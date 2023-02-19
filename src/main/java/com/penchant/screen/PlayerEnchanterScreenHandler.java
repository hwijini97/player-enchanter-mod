package com.penchant.screen;

import com.penchant.PlayerEnchantMod;
import com.penchant.util.StaticPolicy;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;

public class PlayerEnchanterScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public PlayerEnchanterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public PlayerEnchanterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(PlayerEnchantMod.BOX_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 80, 35) {
            @Override
            public boolean canInsert(ItemStack itemStack) {
                return itemStack.getTranslationKey().equals("item.penchant.tanzanite");
            }
        });

        int m;
        int l;
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);

        if (slotIndex == 0 && slots.get(0).getStack().getItem().getTranslationKey().equals("item.penchant.tanzanite")) {
            ClientPlayNetworking.send(Identifier.of(StaticPolicy.MOD_ID, StaticPolicy.PACKET_NAME), PacketByteBufs.empty());
        }
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }
}