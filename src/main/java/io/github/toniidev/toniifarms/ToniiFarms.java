package io.github.toniidev.toniifarms;

import io.github.toniidev.toniifarms.classes.server.ServerPlayer;
import io.github.toniidev.toniifarms.commands.ShowMultipleTasks;
import io.github.toniidev.toniifarms.commands.ShowSingleTasks;
import io.github.toniidev.toniifarms.factories.InputFactory;
import io.github.toniidev.toniifarms.factories.InventoryFactory;
import io.github.toniidev.toniifarms.listeners.PlayerListener;
import io.github.toniidev.toniifarms.utils.InitializeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ToniiFarms extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        InitializeUtils.setMainInstance(this);

        ServerPlayer.load(this);

        new InitializeUtils(new ShowMultipleTasks(this), "consegne").initialize();
        new InitializeUtils(new ShowSingleTasks(), "richieste").initialize();

        Bukkit.getPluginManager().registerEvents(new InventoryFactory(), this);
        Bukkit.getPluginManager().registerEvents(new InputFactory(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
