package org.cws.covens.listener;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.cws.covens.CovensMain;

public class ExpCollect implements Listener {
    public final CovensMain instance = CovensMain.getCovens();

    @EventHandler
    public void onCollect(PlayerPickupExperienceEvent event) {
        Player player = event.getPlayer();
        if ((instance.values.experience.get(player)+1) > (50*instance.values.currentLevel.get(player))) {
            instance.values.experience.put(player,0);
            instance.values.currentLevel.put(player,(instance.values.currentLevel.get(player)+1));
            player.sendActionBar(Component.text("§3Levelup! §e" + instance.values.currentLevel.get(player)));
            player.playSound(player,Sound.BLOCK_BEACON_ACTIVATE,0.5f,2.0f);
        } else {
            instance.values.experience.put(player,(instance.values.experience.get(player)+1));
            player.sendActionBar(Component.text("§3Exp: (" + instance.values.experience.get(player) + "/" + (50*instance.values.currentLevel.get(player)) + ")"));
        }
    }
}
