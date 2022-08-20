package me.notpoiu.endcrystals;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import me.notpoiu.endcrystals.events.EventManager;
import me.notpoiu.endcrystals.item.ItemManager;

public class Plugin extends JavaPlugin{
	
	private Server server;
	
	@Override
	public void onEnable() {
		this.server = this.getServer();
        ItemManager.instance = new ItemManager();
        ItemManager.instance.init();
        this.server.getPluginManager().registerEvents(new EventManager(), this);
        this.server.getConsoleSender().sendMessage(ChatColor.GREEN + "[LegacyCrystals]: Plugin is enabled!");
	}
	
	@Override
	public void onDisable() {
		this.server.getConsoleSender().sendMessage(ChatColor.RED + "[LegacyCrystals]: Plugin is disabled!");
	}
	
}
