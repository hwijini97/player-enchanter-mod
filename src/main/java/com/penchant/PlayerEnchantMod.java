package com.penchant;

import com.penchant.block.PlayerEnchanterBlock;
import com.penchant.block.PlayerEnchanterBlockEntity;
import com.penchant.domain.enumeration.PlayerEnchantment;
import com.penchant.handler.PlayerEnchantButtonHandler;
import com.penchant.handler.PlayerEnchantmentFileHandler;
import com.penchant.handler.applier.PlayerEnchantmentApplyParam;
import com.penchant.item.TanzaniteItem;
import com.penchant.screen.PlayerEnchanterScreenHandler;
import com.penchant.util.StaticPolicy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PlayerEnchantMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(StaticPolicy.MOD_ID);
	public static final TanzaniteItem TANZANITE = new TanzaniteItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
	public static final PlayerEnchanterBlock PLAYER_ENCHANTER_BLOCK = new PlayerEnchanterBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f));
	public static final BlockItem PLAYER_ENCHANTER_ITEM = new BlockItem(PLAYER_ENCHANTER_BLOCK, new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
	public static final BlockEntityType<PlayerEnchanterBlockEntity> PLAYER_ENCHANTER_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(PlayerEnchanterBlockEntity::new, PLAYER_ENCHANTER_BLOCK).build(null);
	public static final ScreenHandlerType<PlayerEnchanterScreenHandler> BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(StaticPolicy.MOD_ID, PlayerEnchanterBlock.NAME), PlayerEnchanterScreenHandler::new);
	public static MinecraftServer SERVER;

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier(StaticPolicy.MOD_ID, TanzaniteItem.NAME), TANZANITE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(TANZANITE));

		Registry.register(Registries.BLOCK, new Identifier(StaticPolicy.MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(StaticPolicy.MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER_ITEM);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(PLAYER_ENCHANTER_BLOCK));

		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(StaticPolicy.MOD_ID, PlayerEnchanterBlock.NAME), PLAYER_ENCHANTER_BLOCK_ENTITY);
		ServerPlayNetworking.registerGlobalReceiver(new Identifier(StaticPolicy.MOD_ID, StaticPolicy.PACKET_NAME), PlayerEnchantButtonHandler::handleEnchantButtonClicked);

		ServerWorldEvents.LOAD.register(this::serveLoaded);
		ServerPlayConnectionEvents.JOIN.register(this::applyEnchantments);
		ServerPlayerEvents.AFTER_RESPAWN.register(this::applyEnchantments);
	}

	private void serveLoaded(MinecraftServer minecraftServer, ServerWorld serverWorld) {
		SERVER = minecraftServer;
	}

	private void applyEnchantments(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
		applyEnchantments(newPlayer);
	}

	private void applyEnchantments(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
		applyEnchantments(serverPlayNetworkHandler.getPlayer());
	}

	private void applyEnchantments(ServerPlayerEntity player) {
		Map<PlayerEnchantment, Integer> playerEnchantmentInfo = PlayerEnchantmentFileHandler.getPlayerEnchantmentInfo(player.getName().getString());

		if (playerEnchantmentInfo.isEmpty()) {
			return;
		}

		player.sendMessage(Text.translatable("penchant.current_applied_enchantments").formatted(Formatting.AQUA));

		for (Map.Entry<PlayerEnchantment, Integer> entry : playerEnchantmentInfo.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				entry.getKey().getApplier().accept(new PlayerEnchantmentApplyParam(player, entry.getKey(), entry.getValue(), false));
			}
			player.sendMessage(Text.translatable("penchant.enchantment_list_item", entry.getKey().getLabel(), entry.getValue() + 1).formatted(Formatting.AQUA));
		}
	}
}
