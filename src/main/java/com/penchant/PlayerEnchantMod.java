package com.penchant;

import com.penchant.item.TanzaniteItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerEnchantMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("penchant");
	public static final TanzaniteItem TANZANITE = new TanzaniteItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier("penchant", TanzaniteItem.NAME), TANZANITE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(TANZANITE));
	}
}
