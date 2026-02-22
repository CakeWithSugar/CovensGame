package org.cws.covens.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.cws.covens.CovensMain;

public class PlayerConsume implements Listener {
    private final CovensMain instance = CovensMain.getCovens();

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (instance.values.coven.get(event.getPlayer()).equals(instance.karmisin.name)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cDu kannst das nicht essen!");
        }
    }
}
