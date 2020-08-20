package coddyfish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import coddyfish.SetBlockDrops;
import coddyfish.guis.DropsGUI;

public class mineListener implements Listener {
	private SetBlockDrops plugin;

	public mineListener(SetBlockDrops plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);
	}

	@EventHandler
	public void OnBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Material mat = e.getBlock().getType();
		String setDrops = plugin.getConfig().getString(p.getName());
		if (setDrops.equals("true")) {
			p.sendMessage("You just broke " + mat);
		}
		System.out.println(DropsGUI.drops);
		Block block = e.getBlock();
		Location blockLoc = block.getLocation();
		if (DropsGUI.drops.containsKey(e.getBlock().getType())) {
				System.out.println(DropsGUI.drops.get(mat));
				DropsGUI.drops.get(mat).dropItem(blockLoc);
			
		}
		DropsGUI.apply(p);

	}

}
