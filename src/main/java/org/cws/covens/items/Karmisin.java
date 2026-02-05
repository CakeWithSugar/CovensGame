package org.cws.covens.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cws.covens.CovensMain;

import java.util.ArrayList;
import java.util.List;

import static org.cws.MainGeneral.main;

public class Karmisin {
    public final CovensMain instance = CovensMain.getCovens();
    public final String name = "Haus des Karmesins";

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
            lore.add("§7Du erhälst §eÜbelkeit§7 wenn du aus der Dunkelheit gehst.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
    public ItemStack abb1(Player player) {
        ItemStack item = new ItemStack(Material.FERMENTED_SPIDER_EYE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cBluthunger");
            List<String> lore = new ArrayList<>();
            lore.add("§8Passive Fähigkeit");
            if (instance.values.ability1Level.get(player) == -1) {
                lore.add("§4[Fähigkeit nicht aktiviert]");
            } else {
                lore.add("§7Level: §b" + (instance.values.ability1Level.get(player)+1));
            }
            lore.add("§7");
            lore.add("§7Erhalte §d+" + (instance.values.ability1Level.get(player)+1) + " §eSättigung §7wenn du ein Lebewesen schlägst.");
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
            lore.add("§7- Teleport");
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
            lore.add("§7");
            lore.add("§7- Schnelligkeit beim töten");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
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
            } else {
                if (player.getWorld().getTime() > 12000) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * (5+instance.values.passiveLevel.get(player)*2), 0, true, true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * (30+instance.values.passiveLevel.get(player)*5), 0, false, true));
                } else {
                player.removePotionEffect(PotionEffectType.STRENGTH);
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 5, 0, true, true));

                }
            }
        },0,100);
    }

    public void abb1Ability(Player player) {
        int abbLevel = instance.values.ability1Level.get(player);
        if (abbLevel == -1) {
            return;
        } else {
            abbLevel = abbLevel+1;
        }
        player.setSaturation(player.getSaturation() + 1);
        player.setFoodLevel(player.getFoodLevel() + abbLevel);
    }
}
