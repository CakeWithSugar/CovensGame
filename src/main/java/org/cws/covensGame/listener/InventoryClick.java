package org.cws.covensGame.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.cws.covensGame.CovensGame;

public class InventoryClick implements Listener {
    CovensGame instance = CovensGame.getInstance();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().equals(instance.particles.setParticle)) {
            event.setCancelled(true);
            int slot = event.getSlot();
            instance.particles.clickManager(slot,event.getClickedInventory(),player);
        }
    }
}
