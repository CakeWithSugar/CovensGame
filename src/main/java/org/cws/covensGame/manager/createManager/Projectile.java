package org.cws.covensGame.manager.createManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.cws.covensGame.CovensGame;

public class Projectile {
  CovensGame instance = CovensGame.getInstance();
  private final String setProjectileName = "§6- Setze ein Projektil -";
  public final Inventory setProjectile = Bukkit.createInventory(null, 9, setProjectileName);

  private void setup(Inventory inventory){
      for (int i = 0;i < inventory.getSize(); i++) {
          inventory.setItem(i,instance.wandBuildingManager.createNameIteme(Material.GRAY_STAINED_GLASS_PANE,"§a"));
      }
      createActivationItem(inventory);
  }

  private void createActivationItem(Inventory inventory){
      inventory.setItem(0,instance.wandBuildingManager.createNameIteme(Material.BARRIER,"§eKein Projektil"));
      inventory.setItem(1,instance.wandBuildingManager.createNameIteme(Material.SNOWBALL,"§eSchneeball"));
      inventory.setItem(2,instance.wandBuildingManager.createNameItemRequirements(Material.ARROW,"§ePfeil",10,2));
      inventory.setItem(3,instance.wandBuildingManager.createNameItemRequirements(Material.TRIDENT,"§eDreizack",20,10));
      inventory.setItem(4,instance.wandBuildingManager.createNameItemRequirements(Material.FIRE_CHARGE,"§eFeuerball",40,20));
      inventory.setItem(5,instance.wandBuildingManager.createNameItemRequirements(Material.WIND_CHARGE,"§eWindkugel",10,5));
      inventory.setItem(6,instance.wandBuildingManager.createNameItemRequirements(Material.EGG,"§eEi",5,1));
      inventory.setItem(7,instance.wandBuildingManager.createNameItemRequirements(Material.ENDER_PEARL,"§eEnder Perle",40,60));
      inventory.setItem(8,instance.wandBuildingManager.createNameItemRequirements(Material.BLAZE_POWDER,"§eKleine Feuerkugel",20,10));
  }

  public void openMenu(Player player,Material material) {
      instance.variables.cancelBuilding(player);
      player.openInventory(setProjectile);
      instance.variables.setupForPlayer(player,material);
      setup(setProjectile);
  }

  public void clickManager(int slot,Player player) {
      if (slot == 0) {
          change(player, "None");
      }
      if (slot == 1) {
          change(player, "Snowball");
      }
      if (slot == 2) {
          change(player, "Arrow");
          instance.variables.addExp(player,10);
          instance.variables.addCooldown(player,2);
      }
      if (slot == 3) {
          change(player, "Trident");
          instance.variables.addExp(player,20);
          instance.variables.addCooldown(player,10);
      }
      if (slot == 4) {
          change(player, "Fireball");
          instance.variables.addExp(player,40);
          instance.variables.addCooldown(player,20);
      }
      if (slot == 5) {
          change(player, "Windcharge");
          instance.variables.addExp(player,10);
          instance.variables.addCooldown(player,5);
      }
      if (slot == 6) {
          change(player, "Egg");
          instance.variables.addExp(player,5);
          instance.variables.addCooldown(player,1);
      }
      if (slot == 7) {
          change(player, "EnderPearl");
          instance.variables.addExp(player,40);
          instance.variables.addCooldown(player,60);
      }
      if (slot == 8) {
          change(player, "SmallFireball");
          instance.variables.addExp(player,20);
          instance.variables.addCooldown(player,10);
      }
      instance.particles.openMenu(player);
  }

  private void change(Player player,String projectile){
      instance.variables.projectile.remove(player);
      instance.variables.projectile.put(player,projectile);
  }
}
