package org.cws.covens.abilities;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.cws.covens.CovensMain;

import java.util.*;

import static org.cws.MainGeneral.main;

public class Gamble {
    public final CovensMain instance = CovensMain.getCovens();
    public final String name = "Hand des Glücks";
    public Map<Player, Entity> bats = new HashMap<>();
    private Map<Player,Boolean> onCooldown = new HashMap<>();

    public ItemStack passive(Player player) {
        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c" + name);
            List<String> lore = new ArrayList<>();
            lore.add("§8Grund Fähigkeit");
            lore.add("§7Level: §b" + (instance.values.passiveLevel.get(player)+1));
            lore.add("§7");
            lore.add("§7Deine neuer Magen verträgt herkömmliche Nahrung nicht.");
            lore.add("§7Du kannst keine §eKonsumierbaren gegenstände§7 zu dir nehmen.");
            lore.add("§7");
            lore.add("§7Außerdem werden deine Sinne in der Dunkelheit verstärkt.");
            lore.add("§7Du erhälst §eNachtsicht§7 für §d"+ (30+instance.values.passiveLevel.get(player)*5) +" §7Sekunden und §eStärke§7 für §d"+ (5+instance.values.passiveLevel.get(player)*2) +" §7Sekunden.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
    public ItemStack abb1(Player player) {
        ItemStack item = new ItemStack(Material.PHANTOM_MEMBRANE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§2Zufallsschutz");
            List<String> lore = new ArrayList<>();
            lore.add("§8Passive Fähigkeit");
            if (instance.values.ability1Level.get(player) == -1) {
                lore.add("§4[Fähigkeit nicht aktiviert]");
                lore.add("§7");
                lore.add("§7Zu einer Wahrscheinlichkeit von §d+" + (10) + "% §7wird Schaden welchen du erhälst, §eignoriert§7.");
            } else {
                lore.add("§7Level: §b" + (instance.values.ability1Level.get(player)+1));
                lore.add("§7");
                lore.add("§7Zu einer Wahrscheinlichkeit von §d+" + ((instance.values.ability1Level.get(player)*2)+10) + "% §7wird Schaden welchen du erhälst, §eignoriert§7.");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack abb2(Player player) {
        ItemStack item = new ItemStack(Material.BAT_SPAWN_EGG);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cFledermaus Teleport");
            List<String> lore = new ArrayList<>();
            lore.add("§8Aktive Fähigkeit");
            if (instance.values.ability2Level.get(player) == -1) {
                lore.add("§4[Fähigkeit nicht aktiviert]");
            } else {
                lore.add("§7Level: §b" + (instance.values.ability2Level.get(player)+1));
            }
            lore.add("§7");
            double time;
            if ((instance.values.ability1Level.get(player)+1) == 0) {
                time = 0.5;
            } else {
                time = ((instance.values.ability2Level.get(player)+1)/2.0);
            }
            lore.add("§7Drücke linksklick während du dich duckst um eine §eFledermaus");
            lore.add("§7für §d" + time +" §7Sekunden fliegen zu lassen.");
            lore.add("§7Nach ablauf der Zeit oder erneuten auslösen wirst du zu ihr teleportiert.");
            lore.add("§7- §e60 §7Sekunden cooldown");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack abb3(Player player) {
        ItemStack item = new ItemStack(Material.OMINOUS_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cTodesrausch");
            List<String> lore = new ArrayList<>();
            lore.add("§8Passive Fähigkeit");
            if (instance.values.ability3Level.get(player) == -1) {
                lore.add("§4[Fähigkeit nicht aktiviert]");
            } else {
                lore.add("§7Level: §b" + (instance.values.ability3Level.get(player)+1));
            }
            int level;
            if ((instance.values.ability1Level.get(player)+1) == 0) {
                level = 5;
            } else {
                level = ((instance.values.ability3Level.get(player)+1) *5);
            }
            lore.add("§7");
            lore.add("§7Erhalte §eSchnelligkeit für §d" + level + " §7Sekunden,");
            lore.add("§7nachdem du ein lebewesen getötet hast.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void particle(Location location) {
        instance.values.playColorParticle("GREEN",location,1.0,5,1,1.0f);
    }

    //Abilities
    public void passiveAbility(Player player) {
        final int[] taskId = new int[1];
        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            if (!player.isOnline()) {
                Bukkit.getScheduler().cancelTask(taskId[0]);
                return;
            }
            int lightLevel = player.getLocation().getBlock().getLightLevel();
            if (lightLevel < 7) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * (5+instance.values.passiveLevel.get(player)*2), 0, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * (30+instance.values.passiveLevel.get(player)*5), 0, false, true));
            } else if (player.getWorld().getTime() > 12000) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * (5 + instance.values.passiveLevel.get(player) * 2), 0, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * (30 + instance.values.passiveLevel.get(player) * 5), 0, false, true));
            }
        },0,100);
    }

    public boolean abb1Ability(Player player) {
        int abbLevel = instance.values.ability1Level.get(player);
        if (abbLevel == -1) {
            return false;
        } else {
            abbLevel = abbLevel+1;
        }
        int random = new Random().nextInt(100);
        return random < (10 + (abbLevel * 2));
    }

    public void abb2Ability(Player player) {
        int abbLevel = instance.values.ability1Level.get(player);
        if (abbLevel == -1 || onCooldown.containsKey(player)) {
            return;
        } else {
            abbLevel = abbLevel+1;
        }
        int time = (abbLevel);
        onCooldown.put(player,true);
        instance.values.setCooldown(player,60);
        Vector direction = player.getEyeLocation().getDirection().normalize().multiply(2);
        Entity bat = player.getWorld().spawnEntity(player.getLocation().add(0,1,0), EntityType.BAT);
        bats.put(player,bat);


        final int[] taskId = new int[2];
        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            if (!bat.isDead()) {
                bat.setVelocity(direction);
                particle(bat.getLocation());
            } else {
                Bukkit.getScheduler().cancelTask(taskId[0]);
            }
        }, 0, 5);
        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (onCooldown.get(player)) {
                teleport(player);
            }
        }, 10L * time);
        Bukkit.getScheduler().runTaskLater(main, () -> {
            onCooldown.remove(player);
        }, 20L * 60);
    }

    public void teleport(Player player) {
        Entity bat = bats.get(player);
        player.teleport(bat);
        player.playSound(player, Sound.ENTITY_PLAYER_TELEPORT,0.5f, 2.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0, false, false));
        bats.remove(player);
        onCooldown.put(player,false);
        bat.remove();
    }

    public void abb3Ability(Player player) {
        int abbLevel = instance.values.ability3Level.get(player);
        if (abbLevel == -1) {
            return;
        } else {
            abbLevel = abbLevel+1;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * (abbLevel * 5), 1, false, true));
    }
}
