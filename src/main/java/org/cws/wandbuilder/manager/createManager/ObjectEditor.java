package org.cws.wandbuilder.manager.createManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.cws.wandbuilder.WandbuilderMain;

import java.util.HashMap;
import java.util.Map;

public class ObjectEditor {
    WandbuilderMain instance = WandbuilderMain.getWandbuilder();
    private final String setObjectEditorName = "§6- Setze die Projektil Werte -";
    public final Inventory setObjectEditor = Bukkit.createInventory(null, 18, setObjectEditorName);
    public Map<Player, Integer> cost = new HashMap<>();

    private void setup(Inventory inventory, Player player){
        for (int i = 0;i < inventory.getSize(); i++) {
            inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
        }
        inventory.setItem(8, instance.wandBuildingManager.getWand(player,false));
        inventory.setItem(4, instance.wandBuildingManager.createNameItemRequirements(Material.LIME_STAINED_GLASS,"§aNächster Schritt",0,0));

        inventory.setItem(11,instance.wandBuildingManager.createCounterItemDouble(Material.ANVIL,"§eGravitation",instance.values.basicGravity));
        inventory.setItem(13,instance.wandBuildingManager.createCounterItemDouble(Material.RABBIT_FOOT,"§eGeschwindigkeit",instance.values.basicSpeed));
        inventory.setItem(15,instance.wandBuildingManager.createCounterItemInt(Material.ARROW,"§eLebensdauer",instance.values.basicTime));
    }

    public void openMenu(Player player) {
        player.openInventory(setObjectEditor);
        cost.put(player,0);
        setup(setObjectEditor,player);
    }

    public void clickManager(int slot,Inventory inventory,Player player,boolean highten) {
        if (slot == 4) {
            instance.destructionEditor.openMenu(player);
            return;
        }
        if (slot == 11) {
            if (highten) {
                instance.variables.gravity.put(player, instance.variables.gravity.get(player) + 0.1);
                instance.wandBuildingManager.changeCounterItemDouble(inventory.getItem(11),0.1);
                instance.variables.addExp(player,-1);
                cost.put(player,cost.get(player)-1);
            } else {
                if (instance.variables.gravity.get(player) - 0.1 >= 0.0) {
                    instance.variables.gravity.put(player, instance.variables.gravity.get(player) - 0.1);
                    instance.wandBuildingManager.changeCounterItemDouble(inventory.getItem(11),-0.1);
                    instance.variables.addExp(player,1);
                    cost.put(player,cost.get(player)+1);
                }
            }
        }
        if (slot == 13) {
            if (highten) {
                instance.variables.speed.put(player, instance.variables.speed.get(player) + 0.1f);
                instance.wandBuildingManager.changeCounterItemDouble(inventory.getItem(13),0.1f);
                instance.variables.addExp(player,2);
                cost.put(player,cost.get(player)+2);
            } else {
                instance.variables.speed.put(player, instance.variables.speed.get(player) - 0.1f);
                instance.wandBuildingManager.changeCounterItemDouble(inventory.getItem(13),-0.1f);
                instance.variables.addExp(player,-2);
                cost.put(player,cost.get(player)-2);
            }
        }
        if (slot == 15) {
            if (highten) {
                instance.variables.time.put(player, instance.variables.time.get(player) + 1);
                instance.wandBuildingManager.changeCounterItemInt(inventory.getItem(15),1);

                instance.variables.addExp(player, 1);
                cost.put(player, cost.get(player) + 1);
            } else {
                if (instance.variables.time.get(player) - 1 > 0) {
                    instance.variables.time.put(player, instance.variables.time.get(player) - 1);
                    instance.wandBuildingManager.changeCounterItemInt(inventory.getItem(15),-1);

                    instance.variables.addExp(player, -1);
                    cost.put(player, cost.get(player) - 1);
                }
            }
        }
        inventory.setItem(8,instance.wandBuildingManager.getWand(player,false));
        inventory.setItem(4, instance.wandBuildingManager.createNameItemRequirements(Material.LIME_STAINED_GLASS,"§aNächster Schritt",cost.get(player),0));
    }
}
