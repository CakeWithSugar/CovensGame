package org.cws;

import org.bukkit.plugin.java.JavaPlugin;
import org.cws.covens.CovensMain;
import org.cws.covens.executors.LevelingExecutor;
import org.cws.wandbuilder.WandbuilderMain;

import java.util.Objects;

public final class MainGeneral extends JavaPlugin {
    public static MainGeneral main;
    CovensMain covensMain = new CovensMain();
    WandbuilderMain wandbuilderMain = new WandbuilderMain();

    public boolean covenActive = true;
    public boolean wandbuilderActive = true;

    @Override
    public void onEnable() {
        main = this;
        enable();
    }

    @Override
    public void onDisable() {
        covensMain.onDisable();
    }

    private void enable() {
        if (covenActive) {
            covensMain.onEnable(getServer(),main);
            Objects.requireNonNull(getCommand("leveling")).setExecutor(new LevelingExecutor());
        }
        if (wandbuilderActive) {
            wandbuilderMain.onEnable(getServer(),main);
        }
    }
}
