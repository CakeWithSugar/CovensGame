package org.cws.covensGame.manager;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cws.covensGame.CovensGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WandBuildingManager {
    CovensGame instance = CovensGame.getInstance();

    public ItemStack getWand(Player player, boolean finalWand) {
        int reqExp = instance.variables.reqExp.get(player);
        int cooldown = instance.variables.cooldown.get(player);

        Material wandType = instance.variables.wandType.get(player);
        List<Particle> particles = instance.variables.particles.get(player);
        String projectile = instance.variables.projectile.get(player);
        String projectileEffect = instance.variables.projectileEffect.get(player);
        int time = instance.variables.time.get(player);
        double gravity = instance.variables.gravity.get(player);
        float speed = instance.variables.speed.get(player);

        ItemStack item = new ItemStack(wandType);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§eProtozauber");
            List<String> lore = new ArrayList<>();
            if (reqExp < 0 && !finalWand) {
                lore.add(instance.values.expRequieredNotation +"§c"+ reqExp +" §6-> 0");
            } else if (!finalWand) {
                lore.add(instance.values.expRequieredNotation + reqExp);
            }
            if (reqExp < 0 && finalWand){
                lore.add(instance.values.expRequieredNotation + 0);
            } else if (finalWand) {
                lore.add(instance.values.expRequieredNotation + reqExp);
            }

            if (cooldown < 0 && !finalWand) {
                lore.add(instance.values.cooldownNotation +"§c"+ cooldown +" §6-> 0");
            } else if (!finalWand) {
                lore.add(instance.values.cooldownNotation + cooldown);
            }
            if (cooldown < 0 && finalWand){
                lore.add(instance.values.cooldownNotation + 0);
            } else if (finalWand) {
                lore.add(instance.values.cooldownNotation + cooldown);
            }
            lore.add("");
            lore.add(instance.values.particleNotation + particles.toString());
            lore.add(instance.values.projectileNotation + projectile);
            lore.add(instance.values.timeNotation + time);
            lore.add(instance.values.gravityNotation + String.format(Locale.US, "%.1f", gravity));
            lore.add(instance.values.speedNotation + String.format(Locale.US, "%.1f", speed));
            lore.add(instance.values.projectileEffectNotation + projectileEffect);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createNameIteme(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createNameItemRequirements(Material material, String name, int exp,int cooldown) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> lore = new ArrayList<>();
            if (exp < 0) {
                lore.add("§3Erhöhung der Levelanforderung: §c" + exp);
            } else {
            lore.add("§3Erhöhung der Levelanforderung: §6+" + exp);
            }
            if (cooldown < 0) {
                lore.add("§3Erhöhung der Ablinkzeit: §a" + cooldown);
            } else {
                lore.add("§3Erhöhung der Ablinkzeit: §a+" + cooldown);
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createCounterItemInt(Material material, String name, int number) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> lore = new ArrayList<>();
            lore.add(instance.values.momentaryNotation + number);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack changeCounterItemInt(ItemStack item, int number) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (!meta.lore().isEmpty()) {
                List<String> lore = new ArrayList<>();
                lore.add(instance.values.momentaryNotation + (instance.loreReaderManager.getLoreInt(item, instance.values.momentaryNotation,0) + number));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

    public ItemStack createCounterItemDouble(Material material, String name, double number) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> lore = new ArrayList<>();
            lore.add(instance.values.momentaryNotation + number);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack changeCounterItemDouble(ItemStack item, double number) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (!meta.lore().isEmpty()) {
                List<String> lore = new ArrayList<>();
                double result = instance.loreReaderManager.getLoreDouble(item, instance.values.momentaryNotation, 0) + number;
                double rounded = Math.round(result * 10) / 10.0;
                lore.add(instance.values.momentaryNotation + rounded);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

    public ItemStack createActivationItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> lore = new ArrayList<>();
            lore.add("§cInaktiv");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack changeActivationItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (!meta.lore().isEmpty()) {
                List<String> lore = new ArrayList<>();
                if (!isActivated(item)) {
                    lore.add("§aAktiviert");
                } else {
                    lore.add("§cInaktiv");
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

    private boolean isActivated(ItemStack item){
        List<String> lore = item.getItemMeta().getLore();
        if (lore != null) {
            for (String line : lore) {
                return !line.contains("§cInaktiv");
            }
        }
        return false;
    }
}
