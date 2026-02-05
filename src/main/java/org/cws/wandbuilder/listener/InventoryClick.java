package org.cws.wandbuilder.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.cws.wandbuilder.WandbuilderMain;

public class InventoryClick implements Listener {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();

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
        if (event.getInventory().equals(instance.values.setObjectEditor)) {
            event.setCancelled(true);
            if (event.isLeftClick()) {
                instance.values.clickManager(slot, event.getClickedInventory(), player,true);
            }
            if (event.isRightClick()) {
                instance.values.clickManager(slot, event.getClickedInventory(), player,false);
            }
        }
        if (event.getInventory().equals(instance.destruction.setDestruction)) {
            event.setCancelled(true);
            instance.destruction.clickManager(slot, player);
        }
    }
}
