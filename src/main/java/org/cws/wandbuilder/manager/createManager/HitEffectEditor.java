package org.cws.wandbuilder.manager.createManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.cws.wandbuilder.WandbuilderMain;

public class HitEffectEditor {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();
    private final String setDesructionName = "§6- Setze ein Ankunftseffekt -";
    public final Inventory setDestruction = Bukkit.createInventory(null, 9, setDesructionName);

    private void setup(Inventory inventory, Player player){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        createActivationItem(inventory,player);
    }

    private void createActivationItem(Inventory inventory,Player player){
        inventory.setItem(2,instance.wandBuildingManager.createNameIteme(Material.GRASS_BLOCK,"§eEffekt auf Blöcke"));
        inventory.setItem(4,instance.wandBuildingManager.createNameIteme(Material.PLAYER_HEAD,"§eEffekte auf Entitäten"));
    }

    public void openMenu(Player player) {
        player.openInventory(setDestruction);
        setup(setDestruction,player);
    }

    public void clickManager(int slot,Player player) {
        if (slot == 1) {
            change(player, "None");
        }
        if (slot == 2) {
            change(player, "Springer");
            instance.variables.addExp(player,5);
            instance.variables.addCooldown(player,(int) Math.ceil(instance.variables.time.get(player) / 4.0));
        }
        if (slot == 3) {
            change(player, "Kreis");
            instance.variables.addExp(player,10);
            instance.variables.addCooldown(player,(int) Math.ceil(instance.variables.time.get(player) / 2.0));
        }
        if (slot == 4) {
            change(player, "Würfel");
            instance.variables.addExp(player,15);
            instance.variables.addCooldown(player,(int) Math.ceil(instance.variables.time.get(player) / 2.0));
        }
        if (slot == 5) {
            change(player, "Kugel");
            instance.variables.addExp(player,20);
            instance.variables.addCooldown(player,(int) Math.ceil(instance.variables.time.get(player) / 2.0));
        }
        instance.variables.confirmBuild(player);
    }

    private void change(Player player,String projectile){
        instance.variables.projectileEffect.remove(player);
        instance.variables.projectileEffect.put(player,projectile);
    }
}
