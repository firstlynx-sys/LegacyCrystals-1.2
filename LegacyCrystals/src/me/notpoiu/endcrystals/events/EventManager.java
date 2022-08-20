package me.notpoiu.endcrystals.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.notpoiu.endcrystals.item.ItemManager;

public class EventManager implements Listener {
	
	@EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null && event.getItem().getItemMeta().equals(ItemManager.instance.crystalItem.getItemMeta()) && (event.getClickedBlock().getType() == Material.OBSIDIAN || event.getClickedBlock().getType() == Material.BEDROCK)) {
            
        	Location loc = new Location(event.getPlayer().getWorld(), (double)event.getClickedBlock().getX() + 0.5, event.getClickedBlock().getY() + 1, (double)event.getClickedBlock().getZ() + 0.5);
        	
        	if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
        		
        		if(event.getItem().getAmount() == 1) {
        			event.getPlayer().getInventory().remove(event.getItem());
        			event.getPlayer().updateInventory();
        		}else {
        			event.getItem().setAmount(event.getItem().getAmount() - 1);
    	            
    	            event.getPlayer().updateInventory();
        		}
        		
	            
            }
        	
        	if(getNearbyEntitiesAndIsEndCrystal(loc, 1.5)) {
        		return;
        	}else {
        		event.getPlayer().getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        	}
            
        }
    }
    
    public static boolean getNearbyEntitiesAndIsEndCrystal(Location where, double range) {
    	boolean amogus = false;
    	
    	for (Entity entity : where.getWorld().getEntities()) {
    		if(amogus)
    			break;
    		
    		if (isInBorder(where, entity.getLocation(), range)) {
    			if(entity.getType() == EntityType.ENDER_CRYSTAL) {
    				amogus = true;
    			}
    		}
    	}
    	
    	return amogus;
    }
    
    public static boolean isInBorder(Location center, Location notCenter, double range) {
    	int x = center.getBlockX(), z = center.getBlockZ();
    	int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();
    	 
    	if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
    		return false;
    	}
    		return true;
    }
    
    
}
