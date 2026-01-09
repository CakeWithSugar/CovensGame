package org.cws.covensGame.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.cws.covensGame.CovensGame;

public class InventoryClose implements Listener {
    CovensGame instance = CovensGame.getInstance();

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().equals(instance.particles.setParticle)) {
            instance.variables.clearForPlayer(player);
        }
    }
}
