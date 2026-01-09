package org.cws.covensGame;

import org.bukkit.plugin.java.JavaPlugin;
import org.cws.covensGame.listener.InventoryClick;
import org.cws.covensGame.listener.InventoryClose;
import org.cws.covensGame.listener.PlayerInteract;
import org.cws.covensGame.manager.CastManager;
import org.cws.covensGame.manager.WandBuildingManager;
import org.cws.covensGame.manager.createManager.Projectile;
import org.cws.covensGame.manager.createManager.Particles;
import org.cws.covensGame.manager.createManager.Variables;

public final class CovensGame extends JavaPlugin {
    public static CovensGame instance;

    public CastManager castManager;
    public WandBuildingManager wandBuildingManager;

    public BaseValues values;
    public Variables variables;

    public Projectile projectile;
    public Particles particles;

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

        projectile = new Projectile();
        particles = new Particles();
    }

    private void setListener() {
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
    }

    public static CovensGame getInstance(){
        return instance;
    }
}
