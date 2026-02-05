package org.cws.wandbuilder.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.cws.wandbuilder.WandbuilderMain;

import java.util.List;

import static org.cws.MainGeneral.main;

public class ProjectileEffectManager {
    WandbuilderMain wandbuilder = WandbuilderMain.getWandbuilder();

    public void castCircle(Location center, List<Particle> particles, double radius,int duration) {
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
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
        }, 0, wandbuilder.baseValues.recursionTicks);
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            Bukkit.getScheduler().cancelTask(task);
        }, 20L *duration);
    }

    public void castCube(Location center, List<Particle> particles, double radius, int duration) {
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            // Points along each edge of the cube
            int pointsPerEdge = 10;
            double halfSize = radius / 2.0;

            // Bottom face (z = -halfSize)
            for (int i = 0; i <= pointsPerEdge; i++) {
                double progress = (double) i / pointsPerEdge;
                // Bottom front edge (y = centerY - halfSize, z = centerZ - halfSize)
                drawLine(
                        center.clone().add(-halfSize, -halfSize, -halfSize),
                        center.clone().add(halfSize, -halfSize, -halfSize),
                        particles, progress
                );
                // Bottom right edge (x = centerX + halfSize, z = centerZ - halfSize)
                drawLine(
                        center.clone().add(halfSize, -halfSize, -halfSize),
                        center.clone().add(halfSize, halfSize, -halfSize),
                        particles, progress
                );
                // Bottom back edge (y = centerY + halfSize, z = centerZ - halfSize)
                drawLine(
                        center.clone().add(halfSize, halfSize, -halfSize),
                        center.clone().add(-halfSize, halfSize, -halfSize),
                        particles, progress
                );
                // Bottom left edge (x = centerX - halfSize, z = centerZ - halfSize)
                drawLine(
                        center.clone().add(-halfSize, halfSize, -halfSize),
                        center.clone().add(-halfSize, -halfSize, -halfSize),
                        particles, progress
                );

                // Top face (z = halfSize)
                // Top front edge (y = centerY - halfSize, z = centerZ + halfSize)
                drawLine(
                        center.clone().add(-halfSize, -halfSize, halfSize),
                        center.clone().add(halfSize, -halfSize, halfSize),
                        particles, progress
                );
                // Top right edge (x = centerX + halfSize, z = centerZ + halfSize)
                drawLine(
                        center.clone().add(halfSize, -halfSize, halfSize),
                        center.clone().add(halfSize, halfSize, halfSize),
                        particles, progress
                );
                // Top back edge (y = centerY + halfSize, z = centerZ + halfSize)
                drawLine(
                        center.clone().add(halfSize, halfSize, halfSize),
                        center.clone().add(-halfSize, halfSize, halfSize),
                        particles, progress
                );
                // Top left edge (x = centerX - halfSize, z = centerZ + halfSize)
                drawLine(
                        center.clone().add(-halfSize, halfSize, halfSize),
                        center.clone().add(-halfSize, -halfSize, halfSize),
                        particles, progress
                );

                // Vertical edges
                drawLine(
                        center.clone().add(-halfSize, -halfSize, -halfSize),
                        center.clone().add(-halfSize, -halfSize, halfSize),
                        particles, progress
                );
                drawLine(
                        center.clone().add(halfSize, -halfSize, -halfSize),
                        center.clone().add(halfSize, -halfSize, halfSize),
                        particles, progress
                );
                drawLine(
                        center.clone().add(halfSize, halfSize, -halfSize),
                        center.clone().add(halfSize, halfSize, halfSize),
                        particles, progress
                );
                drawLine(
                        center.clone().add(-halfSize, halfSize, -halfSize),
                        center.clone().add(-halfSize, halfSize, halfSize),
                        particles, progress
                );
            }
        }, 0, wandbuilder.baseValues.recursionTicks);

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            Bukkit.getScheduler().cancelTask(task);
        }, 20L * duration);
    }

    private void drawLine(Location start, Location end, List<Particle> particles, double progress) {
        Vector to = end.toVector().subtract(start.toVector());
        double length = to.length();
        Vector direction = to.normalize();
        Vector point = start.toVector().add(direction.multiply(length * progress));

        Location particleLoc = new Location(start.getWorld(), point.getX(), point.getY(), point.getZ());

        for (Particle particle : particles) {
            particleLoc.getWorld().spawnParticle(
                    particle,
                    particleLoc,
                    1,    // count
                    0.1,  // offset x
                    0.1,  // offset y
                    0.1,  // offset z
                    0.05  // speed
            );
        }
    }

    public void castJumper(List<Particle> particles, Player player,String projectileString, Projectile projectile, double speed, int duration,double gravity) {
        Vector direction = projectile.getVelocity().add(new Vector(0, 1, 0));
        //Seter
        boolean[] createNext = {false};
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            if (projectile.isDead()) {
                createNext[0] = true;
            }
        }, 0, 1);
        //Checker
        int[] task2 = {0};
        task2[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            if (createNext[0]) {
                Bukkit.getScheduler().cancelTask(task);
                createNext[0] = false;
                double newSpeed = speed - 0.5;
                if (newSpeed < 0) {
                    return;
                }
                double newgravity = gravity + 0.2;
                Location center = projectile.getLocation().toCenterLocation().add(0, 0.1, 0);
                wandbuilder.castManager.buildProjectile(player, direction, center, newSpeed, projectileString, particles, newgravity, duration, "Springer");
                if (Bukkit.getScheduler().isCurrentlyRunning(task2[0])) {
                    Bukkit.getScheduler().cancelTask(task2[0]);
                }
            }
        }, 0, 1);

    }
}
