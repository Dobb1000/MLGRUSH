package net.craftions.main;


import net.craftions.config.Config;
import net.craftions.listeners.Events;
import net.craftions.scoreboard.MlgBoard;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class Main extends JavaPlugin {

    private static Main instance;
    public MlgBoard mlgBoard = new MlgBoard();
    private Events events;


    @Override
    public void onLoad() {
        instance = this;
    }
    @Override
    public void onEnable() {

        PluginManager manager = Bukkit.getPluginManager();



        getLogger().info("MlgRush plugin loaded!");
        File mlgconf = new File("./plugins/MlgRush/config.yml");
        Config config = new Config(mlgconf, "mlgconf");






        if (!mlgconf.exists()) {
            config.set("Redspawn", new Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0, 0, 0));
            config.set("Bluespawn", new Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0, 0, 0));
            config.set("heights.min(reset)", 60);
            config.set("heights.max(construction height)", 100);
            config.set("wins.max", 10);
            getLogger().info("mlg config created");
        }

        config.reload(true);

        manager.registerEvents(new Events(),this);


        }





    @Override
    public void onDisable() {
        getLogger().info("MlgRush plugin unloaded!");

    }



    public static Main getInstance() {
        return instance;
    }

    public Events getJoinEvent() {
        return events;
    }

    }
