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
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null && event.getItem().isSimilar(ItemManager.instance.crystalItem) && (event.getClickedBlock().getType() == Material.OBSIDIAN || event.getClickedBlock().getType() == Material.BEDROCK)) {
            
            Location loc = new Location(event.getPlayer().getWorld(), event.getClickedBlock().getX() + 0.5, event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ() + 0.5);
            Location aboveLoc = new Location(event.getPlayer().getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());

            if (aboveLoc.getBlock().getType() != Material.AIR) {
                return;
            }

            if (getNearbyEntitiesAndIsEndCrystal(loc, 0.5)) {
                return;
            }
            
            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                if (event.getItem().getAmount() == 1) {
                    event.getPlayer().getInventory().remove(event.getItem());
                } else {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                }
                event.getPlayer().updateInventory();
            }
            
            event.getPlayer().getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        }
    }
    
    public boolean getNearbyEntitiesAndIsEndCrystal(Location where, double range) {
        for (Entity entity : where.getWorld().getEntities()) {
            if (isInBorder(where, entity.getLocation(), range) && entity.getType() == EntityType.ENDER_CRYSTAL) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isInBorder(Location center, Location notCenter, double range) {
        double distance = center.distance(notCenter);
        return distance <= range;
    }
}