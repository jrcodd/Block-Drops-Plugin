package coddyfish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import coddyfish.SetBlockDrops;
import coddyfish.guis.DropsGUI;

public class BlockShiftRightClickListener implements Listener {
	SetBlockDrops plugin;

	public BlockShiftRightClickListener(SetBlockDrops plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);

	}

	@EventHandler
	public void OnShiftRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		String setDrops = plugin.getConfig().getString(p.getName());
		if (setDrops.equals("true")) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

				if (p.isSneaking()) {
					Block block = e.getClickedBlock();
					Material mat = block.getType();
					p.sendMessage("you just sneak right clicked on " + mat);
					DropsGUI ui = new DropsGUI();
					p.openInventory(ui.GUI(p, mat));

				}
			}
		} else {
			return;
		}
	}
}
