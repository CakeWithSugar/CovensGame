package org.cws.covens.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.cws.covens.CovensMain;

public class HitEntity implements Listener {
    public final CovensMain instance = CovensMain.getCovens();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (instance.values.coven.get(player).equals(instance.karmisin.name)) {
                if (event.getEntity() instanceof LivingEntity entity) {
                    instance.karmisin.abb1Ability(player);
                    if (entity.getHealth() < event.getDamage()) {
                        instance.karmisin.abb3Ability(player);
                    }
                }
            }
        }
    }
}
