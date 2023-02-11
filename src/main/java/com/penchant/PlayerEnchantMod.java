package com.penchant;

import com.penchant.block.PlayerEnchanterBlock;
import com.penchant.item.TanzaniteItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerEnchantMod implements ModInitializer {
	public static final String MOD_ID = "penchant";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TanzaniteItem TANZANITE = new TanzaniteItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
	public static final PlayerEnchanterBlock PLAYER_ENCHANTER = new PlayerEnchanterBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, TanzaniteItem.NAME), TANZANITE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(TANZANITE));

		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, PlayerEnchanterBlock.NAME), new BlockItem(PLAYER_ENCHANTER, new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(PLAYER_ENCHANTER));
	}
}
