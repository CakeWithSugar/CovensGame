package org.cws.covensGame.manager;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cws.covensGame.CovensGame;

import java.util.ArrayList;
import java.util.List;

public class WandBuildingManager {
    CovensGame instance = CovensGame.getInstance();

    public ItemStack getWand(Material wandType, List<Particle> particles) {
        ItemStack item = new ItemStack(wandType);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§eTeststab");
            List<String> lore = new ArrayList<>();
            lore.add(instance.values.particleNotation + particles.toString());
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
