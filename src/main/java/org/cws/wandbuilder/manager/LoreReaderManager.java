package org.cws.wandbuilder.manager;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LoreReaderManager {
    public String getLoreString(ItemStack item, String notation, int lineNumber) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
            return null;
        }

        List<String> lore = item.getItemMeta().getLore();
        if (lore == null || lore.isEmpty() || lineNumber < 0 || lineNumber >= lore.size()) {
            return null;
        }

        String line = lore.get(lineNumber);
        if (line.contains(notation)) {
            return line.substring(line.indexOf(notation) + notation.length()).trim();
        }
        return null;
    }

    public int getLoreInt(ItemStack item, String notation, int lineNumber) {
        List<String> lore = item.getItemMeta().getLore();
        String line = lore.get(lineNumber);
        if (line.contains(notation)) {
            try {
                String numberStr = line.substring(line.indexOf(notation) + notation.length()).trim();
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public double getLoreDouble(ItemStack item, String notation, int lineNumber) {
        List<String> lore = item.getItemMeta().getLore();
        String line = lore.get(lineNumber);
        if (line.contains(notation)) {
            try {
                String numberStr = line.substring(line.indexOf(notation) + notation.length()).trim();
                return Double.parseDouble(numberStr);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    public float getLoreFloat(ItemStack item, String notation, int lineNumber) {
        List<String> lore = item.getItemMeta().getLore();
        String line = lore.get(lineNumber);
        if (line.contains(notation)) {
            try {
                String numberStr = line.substring(line.indexOf(notation) + notation.length()).trim();
                return Float.parseFloat(numberStr);
            } catch (NumberFormatException e) {
                return 0f;
            }
        }
        return 0f;
    }
}
