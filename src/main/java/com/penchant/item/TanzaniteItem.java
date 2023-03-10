package com.penchant.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class TanzaniteItem extends Item {
    public static final String NAME = "tanzanite";

    public TanzaniteItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.penchant.tanzanite.tooltip").formatted(Formatting.AQUA));
    }

    @Override
    public boolean hasGlint(ItemStack itemStack) {
        return true;
    }
}
