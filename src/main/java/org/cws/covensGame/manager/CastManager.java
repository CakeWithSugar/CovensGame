package org.cws.covensGame.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.cws.covensGame.CovensGame;

import java.util.ArrayList;
import java.util.List;

public class CastManager {
    CovensGame instance = CovensGame.instance;

    public void cast(Player player, ItemStack item) {
        // XP-Level
        int expReq = getLoreInt(item, instance.values.expRequieredNotation,1);
        if (player.getLevel() >= expReq) {
            // Partikel
            List<Particle> particles = new ArrayList<>();
            String particleNames = getLoreString(item, instance.values.particleNotation,0);
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
            String projectile = getLoreString(item, instance.values.particleNotation,2);

            float speedMultiplier = 1;
            boolean gravity = false;
            int time = 10;

            buildProjectile(player, speedMultiplier, projectile, particles, gravity, time);
        }
    }

    private void buildProjectile(Player player, float speedMultiplier, String proj,List<Particle> particle, boolean gravity,int time){
        Vector direction = player.getEyeLocation().getDirection().normalize();
        direction.multiply(speedMultiplier);

        Projectile projectile;
        if (proj.equals("Snowball")) {
            projectile = player.launchProjectile(Snowball.class);
        } else if (proj.equals("Arrow")) {
            projectile = player.launchProjectile(Arrow.class);
        } else {
            projectile = player.launchProjectile(Snowball.class);
            projectile.setVisibleByDefault(false);
        }
        projectile.setVelocity(direction);
        projectile.setGravity(gravity);

        setTrail(projectile,particle);
        setDeathTimer(projectile, time);
    }

    private String getLoreString(ItemStack item, String notation, int lineNumber) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
            return null;
        }

        List<String> lore = item.getItemMeta().getLore();
        if (lore == null || lore.isEmpty() || lineNumber < 0 || lineNumber >= lore.size()) {
            return null;
        }

        String line = lore.get(lineNumber);
        if (line.contains(notation)) {
            return line.substring(line.indexOf(notation) + notation.length()).trim();
        }
        return null;
    }

    private int getLoreInt(ItemStack item, String notation, int lineNumber) {
        List<String> lore = item.getItemMeta().getLore();
        String line = lore.get(lineNumber);
        if (line.contains(notation)) {
            try {
                String numberStr = line.substring(line.indexOf(notation) + notation.length()).trim();
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private void setTrail(Projectile projectile, List<Particle> particle){
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (projectile.isDead()) {
                return;
            }
            for (Particle par : particle) {
                projectile.getWorld().spawnParticle(par, projectile.getLocation(), 1, 0.1, 0.1, 0.1, 0.05);
            }
        }, 0, 1);
    }

    private void setDeathTimer(Projectile projectile, int time){
        if (time == 0) {
            time = 10;
        }
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (projectile.isDead()) {
                return;
            }
            projectile.remove();
        }, 20L * time);
    }
}
