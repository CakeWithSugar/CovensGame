package org.cws.covens.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cws.covens.CovensMain;

public class LevelingExecutor implements CommandExecutor {
    CovensMain instance = CovensMain.getCovens();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            instance.levelingChard.openMenu(player);
        }
        return true;
    }
}
