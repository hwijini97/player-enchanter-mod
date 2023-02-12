package com.penchant;

import com.penchant.block.PlayerEnchanterBlockEntity;
import com.penchant.block.PlayerEnchanterBlock;
import com.penchant.item.TanzaniteItem;
import com.penchant.screen.PlayerEnchanterScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerEnchantMod implements ModInitializer {
	public static final String MOD_ID = "penchant";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TanzaniteItem TANZANITE = new TanzaniteItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
	public static final PlayerEnchanterBlock PLAYER_ENCHANTER_BLOCK = new PlayerEnchanterBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f));
	public static final BlockItem PLAYER_ENCHANTER_ITEM = new BlockItem(PLAYER_ENCHANTER_BLOCK, new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
	public static final BlockEntityType<PlayerEnchanterBlockEntity> PLAYER_ENCHANTER_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(PlayerEnchanterBlockEntity::new, PLAYER_ENCHANTER_BLOCK).build(null);
	public static final ScreenHandlerType<PlayerEnchanterScreenHandler> BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, PlayerEnchanterBlock.NAME), PlayerEnchanterScreenHandler::new);;

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, TanzaniteItem.NAME), TANZANITE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(TANZANITE));

		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER_ITEM);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(PLAYER_ENCHANTER_BLOCK));

		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER_BLOCK_ENTITY);
	}
}
