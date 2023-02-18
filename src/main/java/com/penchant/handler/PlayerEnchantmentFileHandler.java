package com.penchant.handler;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.penchant.domain.enumeration.PlayerEnchantment;
import com.penchant.domain.model.PlayerEnchantmentMap;

import java.io.*;
import java.util.Map;

public class PlayerEnchantmentFileHandler {
    private static final String FILE_PATH = "player_enchantment_data.json";
    private static final Gson gson = new Gson();
    private static final Map<PlayerEnchantment, Integer> EMPTY_PLAYER_ENCHANTMENT = Map.of();
    private static final PlayerEnchantmentMap EMPTY_MAP = new PlayerEnchantmentMap();
    private static final File FILE = new File(FILE_PATH);

    public static PlayerEnchantmentMap getPlayerEnchantmentMap() {
        if (!FILE.exists()) {
            return EMPTY_MAP;
        }

        try {
            Reader reader = new FileReader(FILE_PATH);
            return gson.fromJson(reader, PlayerEnchantmentMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return EMPTY_MAP;
        }
    }

    public static Map<PlayerEnchantment, Integer> getPlayerEnchantmentInfo(String playerName) {
        PlayerEnchantmentMap playerEnchantmentMap = getPlayerEnchantmentMap();

        if (playerEnchantmentMap == null || playerEnchantmentMap.getPlayerEnchantmentMap() == null) {
            return EMPTY_PLAYER_ENCHANTMENT;
        }

        return playerEnchantmentMap.getPlayerEnchantmentMap().getOrDefault(playerName, Map.of());
    }

    public static void addEnchantment(String playerName, PlayerEnchantment enchantment) {
        try {
            PlayerEnchantmentMap map = getPlayerEnchantmentMap();

            if (!map.getPlayerEnchantmentMap().containsKey(playerName)) {
                map.getPlayerEnchantmentMap().put(playerName, Maps.newHashMap());
            }

            if (!map.getPlayerEnchantmentMap().get(playerName).containsKey(enchantment)) {
                map.getPlayerEnchantmentMap().get(playerName).put(enchantment, 1);
            } else {
                map.getPlayerEnchantmentMap().get(playerName).put(enchantment, map.getPlayerEnchantmentMap().get(playerName).get(enchantment) + 1);
            }

            Writer writer = new FileWriter(FILE_PATH);
            gson.toJson(map, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
