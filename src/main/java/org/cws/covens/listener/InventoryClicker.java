package org.cws.covens.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.cws.covens.CovensMain;

public class InventoryClicker implements Listener {
    CovensMain instance = CovensMain.getCovens();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (event.getInventory().equals(instance.covenChoseing.setCoven)) {
            event.setCancelled(true);
            instance.covenChoseing.clickManager(slot, player);
        }
        if (event.getInventory().equals(instance.levelingChard.setAbilities)) {
            event.setCancelled(true);
            instance.levelingChard.clickManager(slot, player);
        }
    }
}
