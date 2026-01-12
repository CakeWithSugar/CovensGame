package org.cws.covensGame.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.cws.covensGame.CovensGame;

import java.util.List;

public class ProjectileEffectManager {
    CovensGame instance = CovensGame.getInstance();

    public void castCircle(Location center, List<Particle> particles, double radius,int duration) {
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            int points = 30;
            double increment = (2 * Math.PI) / points;

            for (int i = 0; i < points; i++) {
                double angle = i * increment;
                double x = center.getX() + (radius * Math.cos(angle));
                double z = center.getZ() + (radius * Math.sin(angle));

                Location point = new Location(center.getWorld(), x, center.getY(), z);
                for (Particle particle : particles) {
                    center.getWorld().spawnParticle(
                            particle,
                            point,
                            1,  // count
                            0.1,  // offset x
                            0.1,  // offset y
                            0.1,  // offset z
                            0.05  // speed
                    );
                }
            }
        }, 0, 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            Bukkit.getScheduler().cancelTask(task);
        }, 20L *duration);
    }
}
