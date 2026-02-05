package org.cws.covens.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.cws.covens.CovensMain;

public class LevelingChard {
    private final CovensMain instance = CovensMain.getCovens();
    private final String setAbilitiesName = "§6- Levele deine Fähigkeiten -";
    public final Inventory setAbilities = Bukkit.createInventory(null, 54, setAbilitiesName);

    private void setup(Inventory inventory, Player player){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.covenItems.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        createItems(inventory,player);
    }

    private void createItems(Inventory inventory,Player player){
        inventory.setItem(19,instance.covenAbilityOrganizer.getPassiveItem(player));
        inventory.setItem(21,instance.covenAbilityOrganizer.getAbb1Item(player));
        inventory.setItem(23,instance.covenAbilityOrganizer.getAbb2Item(player));
        inventory.setItem(25,instance.covenAbilityOrganizer.getAbb3Item(player));
    }

    public void openMenu(Player player) {
        player.openInventory(setAbilities);
        setup(setAbilities,player);
    }

    public void clickManager(int slot,Player player) {
        if (slot == 19) {
            instance.values.passiveLevel.put(player, instance.values.passiveLevel.get(player) + 1);
        }
        if (slot == 21) {
            instance.values.ability1Level.put(player, instance.values.ability1Level.get(player) + 1);
        }
        if (slot == 23) {
            instance.values.ability2Level.put(player, instance.values.ability2Level.get(player) + 1);
        }
        if (slot == 25) {
            instance.values.ability3Level.put(player, instance.values.ability3Level.get(player) + 1);
        }
        openMenu(player);
    }
}
