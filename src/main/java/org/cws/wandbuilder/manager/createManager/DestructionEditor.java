package org.cws.wandbuilder.manager.createManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.cws.wandbuilder.WandbuilderMain;

public class DestructionEditor {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();
    private final String setDesructionName = "§6- Setze ein Ankunftseffekt -";
    public final Inventory setDestruction = Bukkit.createInventory(null, 9, setDesructionName);

    private void setup(Inventory inventory,Player player){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        createActivationItem(inventory,player);
    }

    private void createActivationItem(Inventory inventory,Player player){
        inventory.setItem(1,instance.wandBuildingManager.createNameIteme(Material.BARRIER,"§eKein Effekt"));
        inventory.setItem(2,instance.wandBuildingManager.createNameItemRequirements(Material.SLIME_BALL,"§eSpringer",5,(int) Math.ceil(instance.variables.time.get(player) / 4.0)));
        inventory.setItem(3,instance.wandBuildingManager.createNameItemRequirements(Material.MANGROVE_TRAPDOOR,"§eKreis",10,(int) Math.ceil(instance.variables.time.get(player) / 2.0)));
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
        instance.variables.confirmBuild(player);
    }

    private void change(Player player,String projectile){
        instance.variables.projectileEffect.remove(player);
        instance.variables.projectileEffect.put(player,projectile);
    }
}
