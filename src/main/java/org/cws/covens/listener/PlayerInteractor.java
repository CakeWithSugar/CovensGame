package org.cws.covens.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.cws.covens.CovensMain;

public class PlayerInteractor implements Listener {
    private final CovensMain instance = CovensMain.getCovens();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (instance.values.coven.get(player).equals(instance.karmisin.name)) {
            Material inHand = player.getInventory().getItemInMainHand().getType();
            if ((event.getAction().isLeftClick()) && player.isSneaking() && inHand.equals(Material.AIR)) {
                if (instance.karmisin.bats.containsKey(player)) {
                    instance.karmisin.teleport(player);
                } else {
                    instance.karmisin.abb2Ability(player);
                }
            }
        }
    }
}
