package org.cws.covens.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cws.covens.CovensMain;

public class CovenAbilityOrganizer {
    CovensMain instance = CovensMain.getCovens();

    public ItemStack getPassiveItem(Player player) {
        String coven = instance.values.coven.get(player);
        if (coven.equals(instance.karmisin.name)) {
            return instance.karmisin.passive(player);
        } else if (coven.equals(instance.gamble.name)) {
            return instance.gamble.passive(player);
        }
        return null;
    }

    public ItemStack getAbb1Item(Player player) {
        String coven = instance.values.coven.get(player);
        if (coven.equals(instance.karmisin.name)) {
            return instance.karmisin.abb1(player);
        } else if (coven.equals(instance.gamble.name)) {
            return instance.gamble.abb1(player);
        }
        return null;
    }

    public ItemStack getAbb2Item(Player player) {
        String coven = instance.values.coven.get(player);
        if (coven.equals(instance.karmisin.name)) {
            return instance.karmisin.abb2(player);
        } else if (coven.equals(instance.gamble.name)) {
            return instance.gamble.abb2(player);
        }
        return null;
    }

    public ItemStack getAbb3Item(Player player) {
        String coven = instance.values.coven.get(player);
        if (coven.equals(instance.karmisin.name)) {
            return instance.karmisin.abb3(player);
        } else if (coven.equals(instance.gamble.name)) {
            return instance.gamble.abb3(player);
        }
        return null;
    }

    public void getPassiv(Player player) {
        String coven = instance.values.coven.get(player);
        if (coven.equals(instance.karmisin.name)) {
            instance.karmisin.passiveAbility(player);
        } else if (coven.equals(instance.gamble.name)) {
            instance.gamble.passiveAbility(player);
        }
    }
}
