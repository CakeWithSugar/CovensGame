package org.cws.covens;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class CovensMain{
    public static CovensMain wandbuilder;

    public void onEnable(Server server, Plugin plugin) {
        wandbuilder = this;
        setManager();
        setLists();
        setListener(server,plugin);
    }

    private void setManager() {

    }

    private void setLists() {

    }

    private void setListener(Server server, Plugin plugin) {

    }
}
