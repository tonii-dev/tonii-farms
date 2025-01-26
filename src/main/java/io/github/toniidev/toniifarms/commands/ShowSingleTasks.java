package io.github.toniidev.toniifarms.commands;

import io.github.toniidev.toniifarms.classes.server.ServerPlayer;
import io.github.toniidev.toniifarms.utils.CommandUtils;
import io.github.toniidev.toniifarms.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShowSingleTasks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!CommandUtils.checkPrerequisites(commandSender)) return true;

        Player player = (Player) commandSender;
        ServerPlayer serverPlayer = ServerPlayer.getInstance(player);

        Inventory inventory = serverPlayer.getSingleTasksInventory();

        if(inventory == null){
            player.sendMessage(StringUtils.formatColorCodes('&', "&e[Comando] &cErrore:&7 Non hai richieste da completare al momento."));
            return true;
        }

        player.openInventory(inventory);

        return true;
    }
}
