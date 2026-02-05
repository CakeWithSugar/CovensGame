package org.cws.wandbuilder;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.cws.wandbuilder.listener.InventoryClick;
import org.cws.wandbuilder.listener.PlayerInteract;
import org.cws.wandbuilder.listener.ProjectileHit;
import org.cws.wandbuilder.manager.CastManager;
import org.cws.wandbuilder.manager.LoreReaderManager;
import org.cws.wandbuilder.manager.ProjectileEffectManager;
import org.cws.wandbuilder.manager.WandBuildingManager;
import org.cws.wandbuilder.manager.createManager.*;

public class WandbuilderMain {
    public static WandbuilderMain wandbuilder;

    public CastManager castManager;
    public WandBuildingManager wandBuildingManager;
    public LoreReaderManager loreReaderManager;
    public ProjectileEffectManager projectileEffectManager;

    public BaseValues values;
    public Variables variables;

    public Projectile projectile;
    public Particles particles;
    public ObjectEditor objectEditor;
    public DestructionEditor destructionEditor;

    public void onEnable(Server server,Plugin plugin) {
        wandbuilder = this;
        setManager();
        setLists();
        setListener(server,plugin);
    }

    private void setLists() {
        values = new BaseValues();
        variables = new Variables();
    }

    private void setManager() {
        castManager = new CastManager();
        wandBuildingManager = new WandBuildingManager();
        loreReaderManager = new LoreReaderManager();
        projectileEffectManager = new ProjectileEffectManager();

        projectile = new Projectile();
        particles = new Particles();
        objectEditor = new ObjectEditor();
        destructionEditor = new DestructionEditor();
    }

    private void setListener(Server server,Plugin plugin) {
        server.getPluginManager().registerEvents(new PlayerInteract(), plugin);
        server.getPluginManager().registerEvents(new InventoryClick(), plugin);
        server.getPluginManager().registerEvents(new ProjectileHit(), plugin);
    }

    public static WandbuilderMain getWandbuilder(){
        return wandbuilder;
    }
}
