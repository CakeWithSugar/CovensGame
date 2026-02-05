package org.cws.covens.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.cws.covens.CovensMain;

public class CovenChoseing {
    private final CovensMain instance = CovensMain.getCovens();
    private final String setCovenName = "§6- Setze deinen Coven -";
    public final Inventory setCoven = Bukkit.createInventory(null, 54, setCovenName);

    private void setup(Inventory inventory, Player player){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.covenItems.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        createItems(inventory);
    }

    private void createItems(Inventory inventory){
        inventory.setItem(10,instance.covenItems.createNameIteme(Material.REDSTONE_BLOCK,"§c"+instance.karmisin.name));
    }

    public void openMenu(Player player) {
        player.openInventory(setCoven);
        setup(setCoven,player);
    }

    public void clickManager(int slot,Player player) {
        if (slot == 10) {
            instance.values.coven.put(player,instance.karmisin.name);
            player.closeInventory();
        }
    }
}
