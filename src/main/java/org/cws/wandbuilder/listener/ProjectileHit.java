package org.cws.wandbuilder.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.cws.wandbuilder.WandbuilderMain;

public class ProjectileHit implements Listener {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if(instance.castManager.activeProjectiles.contains(event.getEntity())) {
            event.setCancelled(true);
            event.getEntity().remove();
        }
    }
}
