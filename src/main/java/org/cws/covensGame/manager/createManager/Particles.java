package org.cws.covensGame.manager.createManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.cws.covensGame.CovensGame;

public class Particles {
    CovensGame instance = CovensGame.getInstance();
    private final String setStickName = "§6§l- Setze ein Partikel -";
    public final Inventory setParticle = Bukkit.createInventory(null, 54, setStickName);



    private void setup(Inventory inventory, Material wandType){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        inventory.setItem(8, ItemStack.of(wandType));
        inventory.setItem(4, instance.wandBuildingManager.createNameIteme(Material.LIME_STAINED_GLASS,"§aAbschließen"));

        inventory.setItem(10,instance.wandBuildingManager.createActivationItem(Material.FIREWORK_ROCKET,"§eFeuerwerk"));
        inventory.setItem(11,instance.wandBuildingManager.createActivationItem(Material.FIRE_CHARGE,"§eFlammen"));
        inventory.setItem(12,instance.wandBuildingManager.createActivationItem(Material.BUCKET,"§eWässrig"));
        inventory.setItem(13,instance.wandBuildingManager.createActivationItem(Material.EMERALD,"§eGlizernd grün"));
        inventory.setItem(14,instance.wandBuildingManager.createActivationItem(Material.POWDER_SNOW_BUCKET,"§eSchnee"));
    }

    public void openParticleMenu(Player player, Material wand) {
        player.openInventory(setParticle);
        instance.variables.setupForPlayer(player,wand);
        setup(setParticle,wand);
    }

    public void clickManager(int slot,Inventory inventory,Player player) {
        if (slot == 4) {
            player.closeInventory();
            instance.variables.clearForPlayer(player);
            return;
        }
        if (slot == 10) {
            change(player,Particle.FIREWORK);
        }
        if (slot == 11) {
            change(player,Particle.FLAME);
        }
        if (slot == 12) {
            change(player,Particle.DOLPHIN);
        }
        if (slot == 13) {
            change(player,Particle.COMPOSTER);
        }
        if (slot == 14) {
            change(player,Particle.SNOWFLAKE);
        }
        instance.wandBuildingManager.changeActivationItem(inventory.getItem(slot));
        inventory.setItem(8,instance.wandBuildingManager.getWand(instance.variables.wandType.get(player),instance.variables.particles.get(player)));
    }

    private void change(Player player,Particle particle){
        if (instance.variables.particles.get(player).contains(particle)) {
            instance.variables.particles.get(player).remove(particle);
        } else {
            instance.variables.particles.get(player).add(particle);
        }
    }
}
