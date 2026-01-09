package org.cws.covensGame.manager.createManager;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.cws.covensGame.CovensGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variables {
    CovensGame instance = CovensGame.getInstance();
    public Map<Player, List<Particle>> particles = new HashMap<>();
    public Map<Player, Material> wandType = new HashMap<>();

    public void setupForPlayer(Player player, Material wand){
        List<Particle> chosenParticles = new ArrayList<>();
        particles.put(player,chosenParticles);
        wandType.put(player,wand);
    }

    public void clearForPlayer(Player player){
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(),instance.wandBuildingManager.getWand(wandType.get(player),particles.get(player)));
        particles.remove(player);
        wandType.remove(player);
    }
}
