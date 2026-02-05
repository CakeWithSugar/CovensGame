package org.cws;

import org.bukkit.plugin.java.JavaPlugin;
import org.cws.covens.CovensMain;
import org.cws.wandbuilder.WandbuilderMain;

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
        }
        if (wandbuilderActive) {
            WandbuilderMain wandbuilderMain = new WandbuilderMain();
            wandbuilderMain.onEnable(getServer(),main);
        }
    }
}
