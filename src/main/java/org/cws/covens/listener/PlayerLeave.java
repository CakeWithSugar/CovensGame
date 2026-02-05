package org.cws.covens.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.cws.covens.CovensMain;

public class PlayerLeave implements Listener {
    CovensMain covens = CovensMain.getCovens();

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        covens.values.setup(event.getPlayer(), true);
    }
}
