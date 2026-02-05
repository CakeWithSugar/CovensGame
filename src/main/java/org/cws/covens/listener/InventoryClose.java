package org.cws.covens.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.cws.covens.CovensMain;

import static org.cws.MainGeneral.main;

public class InventoryClose implements Listener {
    CovensMain covens = CovensMain.getCovens();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(covens.covenChoseing.setCoven)) {
            Bukkit.getScheduler().runTaskLater(main, () -> {
                covens.values.setup((Player) event.getPlayer(), false);
            },1);
        }
    }
}
