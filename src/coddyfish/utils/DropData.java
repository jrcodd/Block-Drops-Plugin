package coddyfish.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
/**
 * @author Coddyfish
 * @version 8/19/20 the drop data that can be added to each block
 *
 */
@SerializableAs("BlockDropsData")
public class DropData implements ConfigurationSerializable {
	LinkedHashMap<ItemStack, Double> data;
	public DropData() {
		data = new LinkedHashMap<ItemStack, Double>();
	}
	public DropData(LinkedHashMap<ItemStack, Double> data) {
		this.data = data;
	}
	public LinkedHashMap<ItemStack, Double> getData() {
		return data;
	}
	public void addItem(ItemStack i) {
		data.put(i, 0.0);
	}
	public void addItem(ItemStack i, double chance) {
		data.put(i, chance);
	}
	/**
	 * @author Coddyfish
	 * @version 8/19/20
	 * @param location
	 *            the location in the world to spawn the item in drops the data
	 *            at the location
	 * @calculation generate a random number between 0 and 1, multiply that
	 *              number by 100, if the number is less than the chance, spawn
	 *              the item
	 */
	public void dropItem(Location location) {
		Random r = new Random();
		double d = r.nextDouble() * 100;
		for (ItemStack i : data.keySet()) {
			String itemS = i.toString();
			if (d < data.get(i)) {
				location.getWorld().dropItemNaturally(location, i);

			}
		}
	}
	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		for (ItemStack i : getData().keySet()) {
			result.put("ItemStack", i);
			result.put("ItemStack." + i, getData().get(i));
		}

		return result;
	}
	public int size() {
		return data.keySet().size();
	}
	public void clear() {
		data.clear();
	}

}
