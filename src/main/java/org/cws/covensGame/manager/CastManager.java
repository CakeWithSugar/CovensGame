package org.cws.covensGame.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.cws.covensGame.CovensGame;

import java.util.ArrayList;
import java.util.List;

public class CastManager {
    CovensGame instance = CovensGame.instance;
    public List<Projectile> activeProjectiles = new ArrayList<>();
    public List<Player> onCooldown = new ArrayList<>();

    public void cast(Player player, ItemStack item) {
        if (onCooldown.contains(player)) {
            return;
        }

        int expReq = instance.loreReaderManager.getLoreInt(item, instance.values.expRequieredNotation,0);
        int cooldown = instance.loreReaderManager.getLoreInt(item, instance.values.cooldownNotation,1);

        if (player.getLevel() >= expReq || player.getGameMode() == GameMode.CREATIVE) {
            // Partikel
            List<Particle> particles = new ArrayList<>();
            String particleNames = instance.loreReaderManager.getLoreString(item, instance.values.particleNotation,3);
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
            String projectile = instance.loreReaderManager.getLoreString(item, instance.values.projectileNotation,4);
            int time = instance.loreReaderManager.getLoreInt(item, instance.values.timeNotation,5);
            double gravity = instance.loreReaderManager.getLoreDouble(item, instance.values.gravityNotation,6);
            double speedMultiplier = instance.loreReaderManager.getLoreDouble(item, instance.values.speedNotation,7);
            String projectileEffect = instance.loreReaderManager.getLoreString(item, instance.values.projectileEffectNotation,8);

            buildProjectile(player, speedMultiplier, projectile, particles, gravity, time,projectileEffect);
            setOnCooldown(player,cooldown);
        }
    }

    private void buildProjectile(Player player, double speedMultiplier, String proj,List<Particle> particle, double gravity,int time,String projectileEffect){
        Vector direction = player.getEyeLocation().getDirection().normalize();
        direction.multiply(speedMultiplier);

        Projectile projectile;
        switch (proj) {
            case "Snowball" -> projectile = player.launchProjectile(Snowball.class);
            case "Arrow" -> projectile = player.launchProjectile(Arrow.class);
            case "Trident" -> projectile = player.launchProjectile(Trident.class);
            case "Fireball" -> projectile = player.launchProjectile(Fireball.class);
            case "Windcharge" -> projectile = player.launchProjectile(WindCharge.class);
            case "Egg" -> projectile = player.launchProjectile(Egg.class);
            case "EnderPearl" -> projectile = player.launchProjectile(EnderPearl.class);
            case "SmallFireball" -> projectile = player.launchProjectile(SmallFireball.class);
            default -> {
                projectile = player.launchProjectile(Snowball.class);
                projectile.setVisibleByDefault(false);
            }
        }
        projectile.setVelocity(direction);

        applyGravity(projectile, gravity / 10);
        setTrail(projectile,particle);
        setDeathTimer(projectile, time);
        activeProjectiles.add(projectile);
        castEffect(projectile,particle,projectileEffect,time,speedMultiplier);
    }

    private void castEffect(Projectile projectile, List<Particle> particles, String projectileEffect,int time,double speed) {
        final int[] taskId = new int[1];

        int duration = time/2;
        double radius = speed*1.5;
        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!projectile.isDead() || projectile.isValid()) {
                return;
            }

            if (projectileEffect.equals("Kreis")) {
                instance.projectileEffectManager.castCircle(projectile.getLocation().toCenterLocation(), particles, radius, duration);
            }
            Bukkit.getScheduler().cancelTask(taskId[0]);
        }, 0, 2);
    }

    private void applyGravity(Projectile projectile,double gravityLevel) {
        if (gravityLevel == 0.0) {
            projectile.setGravity(false);
            return;
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!projectile.isValid() || projectile.isDead()) {
                return;
            }
            projectile.setVelocity(projectile.getVelocity().add(new Vector(0, -gravityLevel, 0)));
        },0, 1);
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
            time = instance.values.basicTime;
        }
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if (projectile.isDead()) {
                return;
            }
            if (projectile.getVelocity().isZero()) {
                activeProjectiles.remove(projectile);
                projectile.remove();
            }
        }, 0, 1);

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (projectile.isDead()) {
                return;
            }
            activeProjectiles.remove(projectile);
            projectile.remove();
        }, 20L * time);
    }

    private void setOnCooldown(Player player, int cooldownTime){
        if (cooldownTime != 0) {
            onCooldown.add(player);
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                onCooldown.remove(player);
            }, 20L * cooldownTime);
        }
    }
}
