package coddyfish.guis;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import coddyfish.SetBlockDrops;
import coddyfish.utils.DropData;
import coddyfish.utils.Utils;
/**
 * @author Coddyfish
 * @version 8/19/20 The gui that players can set the drops of blocks with
 */
public class DropsGUI {
	static Material blockClicked;
	static ItemStack itemClicked;
	public static Inventory inv;
	public static LinkedHashMap<Material, DropData> drops;
	public static String inventory_name;
	public static int inv_rows = 36;
	public double chance;
	static SetBlockDrops plugin;

	/**
	 * @author Coddyfish
	 * @version 8/19/20 initialize the variables in the class
	 */
	public void initialize(SetBlockDrops plugin) {
		inventory_name = Utils.chat("&a&lModify Drops");
		inv = Bukkit.createInventory(null, inv_rows);
		drops = new LinkedHashMap<Material, DropData>();
		this.plugin = plugin;
	}
	/**
	 * @author Coddyfish
	 * @version 8/19/20 loads the drops from the config file
	 */
	public void load(SetBlockDrops plugin) {
		ItemStack item;
		Double chance;
		FileConfiguration dropsConfig = plugin.getDropsConfig();
		if (dropsConfig.getConfigurationSection("Drops") == null) {
			System.out.println(
					"there is no confiuration section\nCreating one now...");
			dropsConfig.createSection("Drops");
			if (dropsConfig.getConfigurationSection("Drops") != null) {
				System.out.println("Success!");
			} else {
				System.out.println("Something went wrong...");
				return;
			}
		}
		// config format: Drops.Material_Name.Number.ItemStack.Chance_To_Drop
		// iterate through all the materials in the config section
		for (String mat : dropsConfig.getConfigurationSection("Drops")
				.getKeys(false)) {
			DropData data = new DropData();
			// iterate through all the item numbers
			for (String itemNumber : dropsConfig
					.getConfigurationSection("Drops." + mat).getKeys(false)) {
				item = dropsConfig
						.getItemStack("Drops." + mat + "." + itemNumber, new ItemStack(Material.ACACIA_BOAT));
				chance = dropsConfig.getDouble(
						"Drops." + mat + "." + itemNumber + "." + item, 100.0);
				data.addItem(item, chance);
			}
			drops.put(Material.getMaterial(mat), data);
		}

		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		System.out.println(drops);
	}
	public static void saveData(LinkedHashMap<Material, DropData> drops) {
		// config format: Drops.Material_Name.Number.ItemStack.Chance_To_Drop
		Set<Material> mats = drops.keySet();
		// make the config section if there is not already one
		FileConfiguration dropsConfig = plugin.getDropsConfig();
		if (dropsConfig.getConfigurationSection("Drops") == null) {
			System.out.println(
					"there is no confiuration section\nCreating one now...");
			dropsConfig.createSection("Drops");
			if (dropsConfig.getConfigurationSection("Drops") != null) {
				System.out.println("Success!");
			} else {
				System.out.println("Something went wrong...");
				return;
			}
		}
		// config format: Drops.Material_Name.Number.ItemStack.Chance_To_Drop
		// iterate through all the materials in the config section
		for (Material mat : mats) {
			dropsConfig.set("Drops", mat);
			for (int i = 0; i < drops.get(mat).getData().keySet().size(); i++) {
				ItemStack item = (ItemStack) drops.get(mat).getData().keySet()
						.toArray()[i];
				System.out.println(item);
				dropsConfig.set("Drops." + mat, i);
				dropsConfig.set("Drops." + mat + "." + i, item);
				dropsConfig.set("Drops." + mat.name() + "." + i + "." + item,
						drops.get(mat).getData().get(item));
			}
		}
		try {
			dropsConfig.save(plugin.getDropsFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		System.out.println(drops);

	}
	/**
	 * @author Coddyfish
	 * @version 8/20/20
	 * @param p the player that will open the gui
	 * @param type the material for the inventory to correspond to
	 * @return the inventory for the player to open
	 */
	public static Inventory GUI(Player p, Material type) {
		setBlockClicked(type);
		inventory_name = Utils.chat("&a&l" + type + "'s Drops");
		inv = Bukkit.createInventory(null, inv_rows, inventory_name);
		Inventory toReturn = Bukkit.createInventory(null, inv_rows,
				inventory_name);
		if (drops.containsKey(type)) {


			inv.clear();
			Utils.createItem(inv, Material.BARRIER, 1, 28, "&c&lClear");
			Utils.createItem(inv, Material.GREEN_WOOL, 1, 36, "&2&lApply");

			if (drops.get(type).size() > 0) {

				for (ItemStack i : drops.get(type).getData().keySet()) {
					System.out.println(i);

					ItemMeta meta = i.getItemMeta();
					meta.setDisplayName(
							drops.get(type).getData().get(i).toString());
					i.setItemMeta(meta);
					inv.addItem(i);

					// drops.get(type).remove(i);
				}

			}
			
			} else {
				
			LinkedHashMap<ItemStack, Double> map = new LinkedHashMap<ItemStack, Double>();
			DropData drops = new DropData(map);
			DropsGUI.drops.put(type, drops);
		}
			toReturn = inv;
			System.out.println(inv);
			System.out.println(toReturn);
			return toReturn;
		
	}

	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv,
			ClickType clickType) {
		itemClicked = clicked;

		if (slot == 35 && clicked.getItemMeta().getDisplayName()
				.equals(Utils.chat("&2&lApply"))) {
			apply(p);
		} else if (slot == 27 && clicked.getItemMeta().getDisplayName()
				.equals(Utils.chat("&c&lClear"))) {
			drops.get(blockClicked).clear();
			p.closeInventory();
			p.openInventory(GUI(p, blockClicked));
		} else {
			if (clickType.isLeftClick()) {
				if (clicked == null) {
					p.sendMessage("thats not an item buddy");
				} else {
					if (!drops.get(blockClicked).getData()
							.containsKey(clicked)) {
						p.closeInventory();
						p.getInventory().remove(clicked);
						clicked.setAmount(1);
						drops.get(blockClicked).addItem(clicked, 100.0);
						p.openInventory(GUI(p, blockClicked));
					}
				}
			} else if (clickType.isRightClick()) {
				p.closeInventory();

				Utils.getInput(p, blockClicked, clicked, new String[4]);

				// p.openInventory(GUI(p, blockClickedType));
				// System.out.println(chance);
			}
		}
	}

	public static void apply(Player p) {
		saveData(drops);
		System.out.println(drops);
		p.closeInventory();
		System.out.println(drops);
	}


	public static Material getBlockClicked() {
		return blockClicked;
	}
	public static void setBlockClicked(Material type) {
		DropsGUI.blockClicked = type;
	}
	public static ItemStack getItemClicked() {
		return itemClicked;
	}
	public static void setItemClicked(ItemStack itemClicked) {
		DropsGUI.itemClicked = itemClicked;
	}
	public Inventory getInv() {
		return inv;
	}
	public void setInv(Inventory inv) {
		this.inv = inv;
	}
	public LinkedHashMap<Material, DropData> getDrops() {
		return drops;
	}
	public void setDrops(LinkedHashMap<Material, DropData> drops) {
		this.drops = drops;
	}
	public String getInventory_name() {
		return inventory_name;
	}
	public void setInventory_name(String inventory_name) {
		this.inventory_name = inventory_name;
	}
	public int getInv_rows() {
		return inv_rows;
	}
	public void setInv_rows(int inv_rows) {
		this.inv_rows = inv_rows;
	}
	public double getChance() {
		return chance;
	}
	public void setChance(float chance) {
		this.chance = chance;
	}
	public SetBlockDrops getPlugin() {
		return plugin;
	}
	public void setPlugin(SetBlockDrops plugin) {
		this.plugin = plugin;
	}

}
