package org.cws.wandbuilder.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.cws.wandbuilder.WandbuilderMain;

public class PlayerInteract implements Listener {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) {
            return;
        }
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (item.getType().equals(Material.STICK) || item.getType().equals(Material.BLAZE_ROD) || item.getType().equals(Material.BREEZE_ROD) || item.getType().equals(Material.BOOK) || item.getType().equals(Material.BONE)) {
                event.setCancelled(true);
                if ((event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE))) {
                    instance.projectile.openMenu(player,item.getType());
                    return;
                }
                instance.castManager.cast(player, item);
            }
        }
    }
}
