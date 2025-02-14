package io.github.toniidev.toniifarms.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class InitializeUtils {
    private final CommandExecutor executorClass;
    private List<String> commands;

    public static Plugin mainInstance;

    public static void setMainInstance(Plugin plugin){
        mainInstance = plugin;
    }

    public InitializeUtils(CommandExecutor executor) {
        executorClass = executor;
    }

    public InitializeUtils(CommandExecutor executor, String... commandsToListenTo) {
        executorClass = executor;
        commands = Arrays.asList(commandsToListenTo);
    }

    /**
     * Sets the executor of the command linked to this InitializeUtils instance
     * to the class linked to this InitializeUtils instance
     */
    public void initialize() {
        for (String string : commands) {
            PluginCommand command = Bukkit.getPluginCommand(string);
            if (command == null) {
                Bukkit.getLogger().severe("Plugin command not found " + string);
                return;
            }

            command.setExecutor(this.executorClass);
        }
    }
}
