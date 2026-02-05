package org.cws.wandbuilder.manager.createManager;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.cws.wandbuilder.WandbuilderMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variables {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();
    public Map<Player, Integer> reqExp = new HashMap<>();
    public Map<Player, Integer> cooldown = new HashMap<>();

    public Map<Player, List<Particle>> particles = new HashMap<>();
    public Map<Player, Material> wandType = new HashMap<>();
    public Map<Player, String> projectile = new HashMap<>();

    public Map<Player, Integer> time = new HashMap<>();
    public Map<Player, Double> gravity = new HashMap<>();
    public Map<Player, Float> speed = new HashMap<>();

    public Map<Player, String> projectileEffect = new HashMap<>();

    public void setupForPlayer(Player player, Material wand){
        List<Particle> chosenParticles = new ArrayList<>();
        particles.put(player,chosenParticles);
        wandType.put(player,wand);
        projectile.put(player,instance.values.basicProjectile);
        reqExp.put(player,instance.values.basicExp);
        cooldown.put(player,instance.values.basicCooldown);
        time.put(player,instance.values.basicTime);
        gravity.put(player,instance.values.basicGravity);
        speed.put(player,instance.values.basicSpeed);
        projectileEffect.put(player,instance.values.basicProjectileEffect);
    }

    public void addExp(Player player,int number){
        instance.variables.reqExp.put(player, instance.variables.reqExp.get(player) + number);
    }

    public void addCooldown(Player player,int number){
        instance.variables.cooldown.put(player, instance.variables.cooldown.get(player) + number);
    }

    public void confirmBuild(Player player){
        if (particles.get(player).isEmpty()) {
            particles.get(player).add(instance.values.basicParticle);
        }
player.getInventory().setItem(player.getInventory().getHeldItemSlot(),instance.wandBuildingManager.getWand(player,true));
        player.closeInventory();
    }

    public void cancelBuilding(Player player){
        wandType.remove(player);
        particles.remove(player);
        reqExp.remove(player);
        projectile.remove(player);
        time.remove(player);
        gravity.remove(player);
        speed.remove(player);
        projectileEffect.remove(player);
    }
}
