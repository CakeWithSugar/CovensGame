package org.cws.covensGame;

import org.bukkit.plugin.java.JavaPlugin;
import org.cws.covensGame.listener.InventoryClick;
import org.cws.covensGame.listener.PlayerInteract;
import org.cws.covensGame.manager.CastManager;
import org.cws.covensGame.manager.LoreReaderManager;
import org.cws.covensGame.manager.ProjectileEffectManager;
import org.cws.covensGame.manager.WandBuildingManager;
import org.cws.covensGame.manager.createManager.*;

public final class CovensGame extends JavaPlugin {
    public static CovensGame instance;

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

    @Override
    public void onEnable() {
        instance = this;
        setManager();
        setLists();
        setListener();
    }

    @Override
    public void onDisable() {

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

    private void setListener() {
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    public static CovensGame getInstance(){
        return instance;
    }
}
