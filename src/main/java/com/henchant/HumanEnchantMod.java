package com.henchant;

import com.henchant.item.TanzaniteItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HumanEnchantMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("henchant");
	public static final TanzaniteItem TANZANITE = new TanzaniteItem(new FabricItemSettings().maxCount(1));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier("henchant", TanzaniteItem.NAME), TANZANITE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(TANZANITE));
	}
}