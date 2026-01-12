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
        int slot = event.getSlot();
        if (event.getInventory().equals(instance.particles.setParticle)) {
            event.setCancelled(true);
            instance.particles.clickManager(slot, event.getClickedInventory(), player);
        }
        if (event.getInventory().equals(instance.projectile.setProjectile)) {
            event.setCancelled(true);
            instance.projectile.clickManager(slot, player);
        }
        if (event.getInventory().equals(instance.objectEditor.setObjectEditor)) {
            event.setCancelled(true);
            if (event.isLeftClick()) {
                instance.objectEditor.clickManager(slot, event.getClickedInventory(), player,true);
            }
            if (event.isRightClick()) {
                instance.objectEditor.clickManager(slot, event.getClickedInventory(), player,false);
            }
        }
        if (event.getInventory().equals(instance.destructionEditor.setDestruction)) {
            event.setCancelled(true);
            instance.destructionEditor.clickManager(slot, player);
        }
    }
}
