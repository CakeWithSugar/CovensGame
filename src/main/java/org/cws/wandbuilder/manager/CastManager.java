package org.cws.wandbuilder.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.cws.MainGeneral;
import org.cws.wandbuilder.WandbuilderMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CastManager {
    MainGeneral main = MainGeneral.main;
    WandbuilderMain wandbuilder = WandbuilderMain.wandbuilder;
    public List<Projectile> activeProjectiles = new ArrayList<>();
    public List<Player> onCooldown = new ArrayList<>();

    public void cast(Player player, ItemStack item) {
        if (onCooldown.contains(player)) {
            return;
        }

        int expReq = wandbuilder.loreReaderManager.getLoreInt(item, wandbuilder.values.expRequieredNotation,0);
        int cooldown = wandbuilder.loreReaderManager.getLoreInt(item, wandbuilder.values.cooldownNotation,1);

        if (player.getLevel() >= expReq || player.getGameMode() == GameMode.CREATIVE) {
            // Partikel
            List<Particle> particles = new ArrayList<>();
            String particleNames = wandbuilder.loreReaderManager.getLoreString(item, wandbuilder.values.particleNotation,3);
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
            String projectile = wandbuilder.loreReaderManager.getLoreString(item, wandbuilder.values.projectileNotation,4);
            int time = wandbuilder.loreReaderManager.getLoreInt(item, wandbuilder.values.timeNotation,5);
            double gravity = wandbuilder.loreReaderManager.getLoreDouble(item, wandbuilder.values.gravityNotation,6);
            double speedMultiplier = wandbuilder.loreReaderManager.getLoreDouble(item, wandbuilder.values.speedNotation,7);
            String projectileEffect = wandbuilder.loreReaderManager.getLoreString(item, wandbuilder.values.projectileEffectNotation,8);

            buildProjectile(player,null, null, speedMultiplier, projectile, particles, gravity, time,projectileEffect);
            setOnCooldown(player,cooldown);
        }
    }

    public void buildProjectile(Player player, Vector direction, Location center, double speedMultiplier, String proj, List<Particle> particle, double gravity, int time, String projectileEffect){
        if (direction == null) {
            direction = player.getEyeLocation().getDirection().normalize();
            direction.multiply(speedMultiplier);
        } else {
            direction.multiply(speedMultiplier);
        }

        Projectile projectile;
        if (center == null) {
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
        } else {
            switch (proj) {
                case "Snowball" -> projectile = center.getWorld().spawn(center,Snowball.class);
                case "Arrow" -> projectile = center.getWorld().spawn(center,Arrow.class);
                case "Trident" -> projectile = center.getWorld().spawn(center,Trident.class);
                case "Fireball" -> projectile = center.getWorld().spawn(center,Fireball.class);
                case "Windcharge" -> projectile = center.getWorld().spawn(center,WindCharge.class);
                case "Egg" -> projectile = center.getWorld().spawn(center,Egg.class);
                case "EnderPearl" -> projectile = center.getWorld().spawn(center,EnderPearl.class);
                case "SmallFireball" -> projectile = center.getWorld().spawn(center,SmallFireball.class);
                default -> {
                    projectile = center.getWorld().spawn(center,Snowball.class);
                    projectile.setVisibleByDefault(false);
                }
            }
            if (Objects.equals(projectileEffect, "Springer")) {
                direction.add(new Vector(0,0.1,0));
            }
        }
        projectile.setVelocity(direction);

        applyGravity(projectile, gravity / 10);
        setTrail(projectile,particle);
        setDeathTimer(projectile, time);
        activeProjectiles.add(projectile);
        castEffect(player,projectile,proj,particle,projectileEffect,time,speedMultiplier,gravity);
    }

    private void castEffect(Player player,Projectile projectile,String proj, List<Particle> particles, String projectileEffect,int time,double speed,double gravity) {
        final int[] taskId = new int[1];

        int duration = time/2;
        double radius = speed*1.5;
        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> {
            if (!projectile.isDead() || projectile.isValid()) {
                return;
            }

            if (projectileEffect.equals("Kreis")) {
                wandbuilder.projectileEffectManager.castCircle(projectile.getLocation().toCenterLocation(), particles, radius, duration);
            }
            if (projectileEffect.equals("Würfel")) {
                wandbuilder.projectileEffectManager.castCube(projectile.getLocation().toCenterLocation(), particles, radius, duration);
            }
            if (projectileEffect.equals("Springer")) {
                wandbuilder.projectileEffectManager.castJumper(particles,player,proj, projectile,speed,duration,gravity);
            }
            Bukkit.getScheduler().cancelTask(taskId[0]);
        }, 0, 2);
    }

    private void applyGravity(Projectile projectile,double gravityLevel) {
        if (gravityLevel == 0.0) {
            projectile.setGravity(false);
            return;
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(wandbuilder, () -> {
            if (!projectile.isValid() || projectile.isDead()) {
                return;
            }
            projectile.setVelocity(projectile.getVelocity().add(new Vector(0, -gravityLevel, 0)));
        },0, 1);
    }

    private void setTrail(Projectile projectile, List<Particle> particle){
        Bukkit.getScheduler().runTaskTimer(wandbuilder, () -> {
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
            time = wandbuilder.values.basicTime;
        }
        Bukkit.getScheduler().runTaskTimer(wandbuilder, () -> {
            if (projectile.isDead()) {
                return;
            }
            if (projectile.getVelocity().isZero()) {
                activeProjectiles.remove(projectile);
                projectile.remove();
            }
        }, 0, 1);

        Bukkit.getScheduler().runTaskLater(wandbuilder, () -> {
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
            Bukkit.getScheduler().runTaskLater(wandbuilder, () -> {
                onCooldown.remove(player);
            }, 20L * cooldownTime);
        }
    }
}
