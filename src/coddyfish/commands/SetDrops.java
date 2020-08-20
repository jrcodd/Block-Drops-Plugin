package coddyfish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coddyfish.SetBlockDrops;
import coddyfish.utils.Utils;
/**
 * @author Coddyfish
 * @version 8/19/20
 * the class to handle the command that toggles wheather or not someone is going to set the drops of a block
 */
public class SetDrops implements CommandExecutor {

	SetBlockDrops plugin;

	public SetDrops(SetBlockDrops plugin) {
		this.plugin = plugin;
		plugin.getCommand("togglesetdrops").setExecutor(this);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//only handle the command if the person is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command");
			return true;
		}
		//set the player to the person that sent the command
		Player p = (Player) sender;
		//only allow them to do the command if they have the permission
		if (!(p.hasPermission("collection.admin"))) {
			p.sendMessage(Utils.chat("&cYou do not have permission to perform this command"));
			return true;
		}
		//set status to the value from the config file
		String status = plugin.getConfig().getString(p.getName());
		//toggle wheather or not they can set drops of the blocks
		if (status.equals("false")) {
			p.sendMessage("You will now be able to set drops of blocks!");
			plugin.getConfig().set(p.getName(), "true");
		}
		if (status.equals("true")) {

			p.sendMessage("You will now not be able to set drops of blocks!");
			plugin.getConfig().set(p.getName(), "false");
		}
		return true;
	}

}