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
    
    private void setup(Inventory inventory, Player player){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        inventory.setItem(8, instance.wandBuildingManager.getWand(player));
        inventory.setItem(4, instance.wandBuildingManager.createNameIteme(Material.LIME_STAINED_GLASS,"§aAbschließen"));

        inventory.setItem(10,instance.wandBuildingManager.createActivationItem(Material.FIREWORK_ROCKET,"§eFeuerwerk"));
        inventory.setItem(11,instance.wandBuildingManager.createActivationItem(Material.FIRE_CHARGE,"§eFlammen"));
        inventory.setItem(12,instance.wandBuildingManager.createActivationItem(Material.WATER_BUCKET,"§eWässrig"));
        inventory.setItem(13,instance.wandBuildingManager.createActivationItem(Material.EMERALD,"§eGlizernd grün"));
        inventory.setItem(14,instance.wandBuildingManager.createActivationItem(Material.POWDER_SNOW_BUCKET,"§eSchnee"));
        inventory.setItem(15,instance.wandBuildingManager.createActivationItem(Material.WHITE_WOOL,"§eWolken"));
        inventory.setItem(16,instance.wandBuildingManager.createActivationItem(Material.CRIMSON_FUNGUS,"§eKarminrot"));

        inventory.setItem(19,instance.wandBuildingManager.createActivationItem(Material.ENDER_PEARL,"§eEnder"));
        inventory.setItem(20,instance.wandBuildingManager.createActivationItem(Material.LIGHTNING_ROD,"§eElektrisch"));
        inventory.setItem(21,instance.wandBuildingManager.createActivationItem(Material.END_ROD,"§eEndstab"));
        inventory.setItem(22,instance.wandBuildingManager.createActivationItem(Material.GLOWSTONE,"§eLeuchtend"));
        inventory.setItem(23,instance.wandBuildingManager.createActivationItem(Material.GLOW_INK_SAC,"§eLeuchtende Tinte"));
        inventory.setItem(24,instance.wandBuildingManager.createActivationItem(Material.NAUTILUS_SHELL,"§eNautilus"));
        inventory.setItem(25,instance.wandBuildingManager.createActivationItem(Material.OBSIDIAN,"§ePortal"));

        inventory.setItem(28,instance.wandBuildingManager.createActivationItem(Material.FLINT,"§eSilberfunkel"));
        inventory.setItem(29,instance.wandBuildingManager.createActivationItem(Material.SCULK,"§eSculk"));
        inventory.setItem(30,instance.wandBuildingManager.createActivationItem(Material.SCULK_CATALYST,"§eSculk-Katalysator"));
        inventory.setItem(31,instance.wandBuildingManager.createActivationItem(Material.CAMPFIRE,"§eRauch"));
        inventory.setItem(32,instance.wandBuildingManager.createActivationItem(Material.BONE,"§eNiesen"));
        inventory.setItem(33,instance.wandBuildingManager.createActivationItem(Material.SOUL_SAND,"§eSeele"));
        inventory.setItem(34,instance.wandBuildingManager.createActivationItem(Material.SOUL_TORCH,"§eSeelenflamme"));

        inventory.setItem(37,instance.wandBuildingManager.createActivationItem(Material.CAULDRON,"§eMagie"));
        inventory.setItem(38,instance.wandBuildingManager.createActivationItem(Material.POTION,"§eHexerei"));
        inventory.setItem(39,instance.wandBuildingManager.createActivationItem(Material.SPIDER_EYE,"§eSpucken"));
        inventory.setItem(40,instance.wandBuildingManager.createActivationItem(Material.INK_SAC,"§eTinte"));
        inventory.setItem(41,instance.wandBuildingManager.createActivationItem(Material.TOTEM_OF_UNDYING,"§eTotem"));
    }

    public void openParticleMenu(Player player, Material wand) {
        instance.variables.cancelBuilding(player);
        player.openInventory(setParticle);
        instance.variables.setupForPlayer(player,wand);
        setup(setParticle,player);
    }

    public void clickManager(int slot,Inventory inventory,Player player) {
        if (slot == 4) {
            instance.projectile.openProjectileMenu(player);
            return;
        }
        if (slot == 10) {
            change(player, Particle.FIREWORKS_SPARK);
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
        if (slot == 15) {
            change(player,Particle.CLOUD);
        }
        if (slot == 16) {
            change(player,Particle.CRIMSON_SPORE);
        }
        
        if (slot == 19) {
            change(player,Particle.CRIT_MAGIC);
        }
        if (slot == 20) {
            change(player,Particle.ELECTRIC_SPARK);
        }
        if (slot == 21) {
            change(player,Particle.END_ROD);
        }
        if (slot == 22) {
            change(player,Particle.GLOW);
        }
        if (slot == 23) {
            change(player,Particle.GLOW_SQUID_INK);
        }
        if (slot == 24) {
            change(player,Particle.NAUTILUS);
        }
        if (slot == 25) {
            change(player,Particle.PORTAL);
        }

        if (slot == 28) {
            change(player,Particle.SCRAPE);
        }
        if (slot == 29) {
            change(player,Particle.SCULK_CHARGE);
        }
        if (slot == 30) {
            change(player,Particle.SCULK_SOUL);
        }
        if (slot == 31) {
            change(player,Particle.SMOKE_NORMAL);
        }
        if (slot == 32) {
            change(player,Particle.SNEEZE);
        }
        if (slot == 33) {
            change(player,Particle.SOUL);
        }
        if (slot == 34) {
            change(player,Particle.SOUL_FIRE_FLAME);
        }

        if (slot == 37) {
            change(player,Particle.SPELL);
        }
        if (slot == 38) {
            change(player,Particle.SPELL_WITCH);
        }
        if (slot == 39) {
            change(player,Particle.SPIT);
        }
        if (slot == 40) {
            change(player,Particle.SQUID_INK);
        }
        if (slot == 41) {
            change(player,Particle.TOTEM);
        }
        if (inventory.getItem(slot).getItemMeta().hasLore() && slot != 8) {
            instance.wandBuildingManager.changeActivationItem(inventory.getItem(slot));
        }
        inventory.setItem(8,instance.wandBuildingManager.getWand(player);
    }

    private void change(Player player,Particle particle){
        if (instance.variables.particles.get(player).contains(particle)) {
            instance.variables.particles.get(player).remove(particle);
            instance.variables.reqExp.put(player, instance.variables.reqExp.get(player) - instance.variables.particles.get(player).size());
        } else {
            instance.variables.particles.get(player).add(particle);
            instance.variables.reqExp.put(player, instance.variables.reqExp.get(player) + instance.variables.particles.get(player).size()-1);
        }
    }
}
