package coddyfish;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import coddyfish.commands.SetDrops;
import coddyfish.guis.DropsGUI;
import coddyfish.listeners.BlockShiftRightClickListener;
import coddyfish.listeners.InventoryClickedListener;
import coddyfish.listeners.PlayerJoinListener;
import coddyfish.listeners.mineListener;
import coddyfish.utils.DropData;
import coddyfish.utils.SignMenuFactory;
import coddyfish.utils.Utils;
/**
 * 
 * @author Coddyfish
 * @version 8/19/20 main plugin class
 *
 */
public class SetBlockDrops extends JavaPlugin {
	private File dropsFile;
	private FileConfiguration dropsConfig;
	private SignMenuFactory signMenuFactory;
	private DropsGUI ui;
	@Override
	public void onEnable() {
		this.signMenuFactory = new SignMenuFactory(this);
		saveDefaultConfig();
		loadFiles();
		new SetDrops(this);
		ui = new DropsGUI();
		ui.initialize(this);
		ui.load(this);
		Utils.initialize(this);
		new BlockShiftRightClickListener(this);
		new InventoryClickedListener(this);
		new mineListener(this);
		new PlayerJoinListener(this);
		super.onEnable();
	}
	/**
	 * @author Coddyfish
	 * @version 8/19/20 loads the config file DropsData.yml
	 * 
	 */
	public void loadFiles() {
		dropsFile = new File(getDataFolder(), "DropsData.yml");
		dropsConfig = YamlConfiguration.loadConfiguration(dropsFile);
		if (!dropsFile.exists()) {
			saveResource("DropsData.yml", false);
		}
		try {
			dropsConfig.load(dropsFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author Coddyfish
	 * @version 8/19/20
	 * @return the drops file
	 */
	public File getDropsFile() {
		return dropsFile;
	}
	/**
	 * @author Coddyfish
	 * @version 8/19/20
	 * @return the drops file config
	 */
	public FileConfiguration getDropsConfig() {
		return dropsConfig;
	}

	@Override
	public void onDisable() {
		// signGUI.destroy();
		saveDefaultConfig();
		super.onDisable();
	}
	/**
	 * @author Coddyfish
	 * @version 8/19/20
	 * @return the sign menu factory
	 */
	public SignMenuFactory getSignMenuFactory() {
		return this.signMenuFactory;
	}

}
