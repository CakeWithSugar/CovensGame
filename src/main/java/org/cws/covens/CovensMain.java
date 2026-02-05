package org.cws.covens;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.cws.covens.executors.LevelingExecutor;
import org.cws.covens.items.Karmisin;
import org.cws.covens.listener.*;
import org.cws.covens.manager.CovenAbilityOrganizer;
import org.cws.covens.manager.CovenChoseing;
import org.cws.covens.manager.CovenItems;
import org.cws.covens.manager.LevelingChard;

import java.util.Objects;

public final class CovensMain{
    public static CovensMain covens;
    public Values values;
    public CovenItems covenItems;
    public CovenChoseing covenChoseing;

    public Karmisin karmisin;
    public CovenAbilityOrganizer covenAbilityOrganizer;
    public LevelingChard levelingChard;

    public void onEnable(Server server, Plugin plugin) {
        covens = this;
        setAbilities();
        setManager();
        setListener(server,plugin);
    }

    private void setAbilities() {
        karmisin = new Karmisin();
    }

    private void setManager() {
        values = new Values();
        covenItems = new CovenItems();
        covenChoseing = new CovenChoseing();
        covenAbilityOrganizer = new CovenAbilityOrganizer();
        levelingChard = new LevelingChard();
    }

    private void setListener(Server server, Plugin plugin) {
        server.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        server.getPluginManager().registerEvents(new PlayerLeave(), plugin);
        server.getPluginManager().registerEvents(new InventoryClose(), plugin);
        server.getPluginManager().registerEvents(new InventoryClicker(), plugin);
        server.getPluginManager().registerEvents(new Eat(), plugin);
        server.getPluginManager().registerEvents(new HitEntity(), plugin);
    }

    public static CovensMain getCovens(){
        return covens;
    }
}
