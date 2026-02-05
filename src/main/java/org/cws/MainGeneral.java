package org.cws;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.cws.covens.CovensMain;
import org.cws.wandbuilder.WandbuilderMain;

public final class MainGeneral extends JavaPlugin {
    public static MainGeneral main;

    @Override
    public void onEnable() {
        main = this;
        enable();
    }

    private void enable() {
        WandbuilderMain wandbuilderMain = new WandbuilderMain();
        wandbuilderMain.onEnable(getServer(),main);
        CovensMain covensMain = new CovensMain();
        covensMain.onEnable(getServer(),main);
    }
}
