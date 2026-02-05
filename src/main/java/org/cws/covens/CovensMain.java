package org.cws.covens;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.cws.covens.listener.InventoryClicker;
import org.cws.covens.listener.InventoryClose;
import org.cws.covens.listener.PlayerJoin;
import org.cws.covens.listener.PlayerLeave;
import org.cws.covens.manager.CovenChoseing;
import org.cws.covens.manager.CovenItems;

public final class CovensMain{
    public static CovensMain covens;
    public Values values;
    public CovenItems covenItems;
    public CovenChoseing covenChoseing;

    public void onEnable(Server server, Plugin plugin) {
        covens = this;
        setManager();
        setListener(server,plugin);
    }

    private void setManager() {
        values = new Values();
        covenItems = new CovenItems();
        covenChoseing = new CovenChoseing();
    }

    private void setListener(Server server, Plugin plugin) {
        server.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        server.getPluginManager().registerEvents(new PlayerLeave(), plugin);
        server.getPluginManager().registerEvents(new InventoryClose(), plugin);
        server.getPluginManager().registerEvents(new InventoryClicker(), plugin);
    }

    public static CovensMain getCovens(){
        return covens;
    }
}
