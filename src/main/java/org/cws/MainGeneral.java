package org.cws;

import org.bukkit.plugin.java.JavaPlugin;
import org.cws.covens.CovensMain;
import org.cws.covens.executors.LevelingExecutor;
import org.cws.wandbuilder.WandbuilderMain;

import java.util.Objects;

public final class MainGeneral extends JavaPlugin {
    public static MainGeneral main;

    public boolean covenActive = true;
    public boolean wandbuilderActive = false;

    @Override
    public void onEnable() {
        main = this;
        enable();
    }

    private void enable() {
        if (covenActive) {
            CovensMain covensMain = new CovensMain();
            covensMain.onEnable(getServer(),main);
            Objects.requireNonNull(getCommand("leveling")).setExecutor(new LevelingExecutor());
        }
        if (wandbuilderActive) {
            WandbuilderMain wandbuilderMain = new WandbuilderMain();
            wandbuilderMain.onEnable(getServer(),main);
        }
    }
}
