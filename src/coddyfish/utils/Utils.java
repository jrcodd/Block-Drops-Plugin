package coddyfish.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.login.Configuration;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import coddyfish.SetBlockDrops;
import coddyfish.guis.DropsGUI;

public class Utils {
	static SetBlockDrops plugin;
	public static void initialize(SetBlockDrops plugin) {
		Utils.plugin = plugin;

	}
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static ItemStack createItem(Inventory inv, Material material,
			int amount, int invSlot, String displayName, String... loreString) {
		List<String> lore = new ArrayList<String>();
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(chat(displayName));
		byte b;
		int i;
		String[] arrayOfString;
		for (i = (arrayOfString = loreString).length, b = 0; b < i;) {
			String s = arrayOfString[b];
			lore.add(chat(s));
			b++;
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(invSlot - 1, item);
		return item;
	}

	public static void getInput(Player p, Material mat, ItemStack item,
			String[] message) {

		String toReturn = "";
		plugin.getSignMenuFactory().newMenu(Arrays.asList(message))
				.reopenIfFail().response((player, lines) -> {
					if (Double.parseDouble(lines[2]) > 0
							|| Double.parseDouble(lines[2]) < 100) {
						DropsGUI.drops.get(mat).addItem(item,
								Double.parseDouble(lines[2]));
						return true;
					}
					return false;
				}).open(p);
	}
	
}
