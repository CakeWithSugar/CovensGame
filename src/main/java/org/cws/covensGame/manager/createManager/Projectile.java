package org.cws.covensGame.manager.createManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.cws.covensGame.CovensGame;

public class Projectile {
  CovensGame instance = CovensGame.getInstance();
  private final String setProjectileName = "§6§l- Setze ein Projektil -";
  public final Inventory setProjectile = Bukkit.createInventory(null, 18, setProjectileName);

  private void setup(Inventory inventory, Player player){
      for (int i = 0;i < inventory.getSize(); i++) {
          inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
      }
      inventory.setItem(8, instance.wandBuildingManager.getWand(player));
      inventory.setItem(4, instance.wandBuildingManager.createNameIteme(Material.LIME_STAINED_GLASS,"§aAbschließen"));

      inventory.setItem(10,instance.wandBuildingManager.createActivationItem(Material.BARRIER,"§eKein Projektiel"));
      inventory.setItem(11,instance.wandBuildingManager.createActivationItem(Material.SNOW_BALL,"§eSchneeball"));
      inventory.setItem(12,instance.wandBuildingManager.createActivationItem(Material.ARROW,"§ePfeil"));
  }

  public void openProjectileMenu(Player player) {
      player.openInventory(setProjectile);
      setup(setProjectile,player);
  }

  public void clickManager(int slot,Inventory inventory,Player player) {
      if (slot == 4) {
          instance.variables.confirmBuild(player);
          return;
      }
      if (slot == 10) {
          change(player, "None");
      }
      if (slot == 11) {
          change(player, "Snowball");
      }
      if (slot == 12) {
          change(player, "Arrow");
      }
      if (inventory.getItem(slot).getItemMeta().hasLore() && slot != 8) {
          instance.wandBuildingManager.changeActivationItem(inventory.getItem(slot));
      }
      inventory.setItem(8,instance.wandBuildingManager.getWand(player);
  }

  private void change(Player player,String projectile){
      instance.variables.projectile.remove(player);
      instance.variables.projectile.put(player,projectile);
  }
}
