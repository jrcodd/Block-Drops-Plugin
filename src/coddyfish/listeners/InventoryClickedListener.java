package coddyfish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

import coddyfish.SetBlockDrops;
import coddyfish.guis.DropsGUI;

public class InventoryClickedListener implements Listener {
	private SetBlockDrops plugin;

	public InventoryClickedListener(SetBlockDrops plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);
	}
/**
 * @author jrcodd
 * @version 8/20/20
 * @param e the click event
 */
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String title = e.getView().getTitle();
		System.out.println(DropsGUI.inventory_name);
		if (title.equals(DropsGUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}

			DropsGUI.clicked((Player) e.getWhoClicked(), e.getSlot(),
					e.getCurrentItem(), e.getInventory(), e.getClick());
		}
	}
}
