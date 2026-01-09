package org.cws.covensGame.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.cws.covensGame.CovensGame;

import java.util.ArrayList;
import java.util.List;

public class CastManager {
    CovensGame instance = CovensGame.instance;

    public void cast(Player player, ItemStack item) {
        List<Particle> particles = new ArrayList<>();
        String particleNames = getLoreString(item, instance.values.particleNotation);

        if (particleNames != null && !particleNames.isEmpty()) {
            String[] particleArray = particleNames
                    .replace("[", "")
                    .replace("]", "")
                    .split(",\\s*");

            for (String name : particleArray) {
                try {
                    if (!name.trim().isEmpty()) {
                        particles.add(Particle.valueOf(name.trim().toUpperCase()));
                    }
                } catch (IllegalArgumentException e) {
                    player.sendMessage("§cUngültiges Partikel: " + name);
                }
            }
        }

        float yawOffset = 0;
        float speedMultiplier = 1;
        Location spawnLocation = null;
        boolean gravity = false;
        int time = 10;

        buildProjectile(player, yawOffset, speedMultiplier, spawnLocation,particles,gravity,time);
    }

    private void buildProjectile(Player player, float yawOffset, float speedMultiplier, Location spawnLocation,List<Particle> particle, boolean gravity,int time){
        Snowball snowball;
        float yaw = player.getLocation().getYaw() + yawOffset;
        float pitch = player.getLocation().getPitch();

        double radianYaw = Math.toRadians(yaw);
        double radianPitch = Math.toRadians(pitch);

        double x = -Math.sin(radianYaw) * Math.cos(radianPitch);
        double y = -Math.sin(radianPitch);
        double z = Math.cos(radianYaw) * Math.cos(radianPitch);

        Vector direction = new Vector(x, y, z).normalize().multiply(speedMultiplier);
        if (spawnLocation != null) {
            snowball = spawnLocation.getWorld().spawn(spawnLocation, Snowball.class);
        } else {
            snowball = player.launchProjectile(Snowball.class);
        }
        snowball.setVelocity(direction);
        snowball.setGravity(gravity);

        setTrail(snowball,particle);
        setDeathTimer(snowball, time);
    }

    private String getLoreString(ItemStack item, String notation) {
        List<String> lore = item.getItemMeta().getLore();
        if (lore != null) {
            for (String line : lore) {
                if (line.contains(notation)) {
                    return line.substring(line.indexOf(notation) + notation.length()).trim();
                }
            }
        }
        return null;
    }

    private void setTrail(Snowball snowball, List<Particle> particle){
        snowball.setVisibleByDefault(false);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (snowball.isDead()) {
                return;
            }
            for (Particle par : particle) {
                snowball.getWorld().spawnParticle(par, snowball.getLocation(), 1, 0.1, 0.1, 0.1, 0.1);
            }
        }, 0, 1);
    }

    private void setDeathTimer(Snowball snowball, int time){
        if (time == 0) {
            time = 10;
        }
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (snowball.isDead()) {
                return;
            }
            snowball.remove();
        }, 20L * time);
    }
}
