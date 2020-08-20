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

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		DropsGUI ui = new DropsGUI();
		String title = e.getView().getTitle();
		if (title.equals(ui.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}

			ui.clicked((Player) e.getWhoClicked(), e.getSlot(),
					e.getCurrentItem(), e.getInventory(), e.getClick());
		}
	}
}
