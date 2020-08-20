package coddyfish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import coddyfish.SetBlockDrops;

public class PlayerJoinListener implements Listener {
	SetBlockDrops plugin;

	public PlayerJoinListener(SetBlockDrops plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		plugin.getConfig().set(p.getName(), "false");
		plugin.saveConfig();
	}

}
