package org.cws.covens.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cws.covens.CovensMain;

public class PlayerJoin implements Listener {
    CovensMain covens = CovensMain.getCovens();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        covens.values.setup(player,false);
    }
}
