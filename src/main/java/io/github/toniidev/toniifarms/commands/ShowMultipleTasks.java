package io.github.toniidev.toniifarms.commands;

import io.github.toniidev.toniifarms.classes.server.ServerPlayer;
import io.github.toniidev.toniifarms.factories.InventoryFactory;
import io.github.toniidev.toniifarms.factories.MultipleInventoryFactory;
import io.github.toniidev.toniifarms.utils.CommandUtils;
import io.github.toniidev.toniifarms.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ShowMultipleTasks implements CommandExecutor {
    private final Plugin p;

    public ShowMultipleTasks(Plugin main){
        p = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!CommandUtils.checkPrerequisites(commandSender)) return true;

        Player player = (Player) commandSender;
        ServerPlayer serverPlayer = ServerPlayer.getInstance(player);

        Inventory inventory = serverPlayer.getMultipleTasksInventory();

        if(inventory == null){
            player.sendMessage(StringUtils.formatColorCodes('&', "&e[Comando] &cErrore:&7 Non hai consegne da completare al momento."));
            return true;
        }

        player.openInventory(inventory);

        return true;
    }
}
