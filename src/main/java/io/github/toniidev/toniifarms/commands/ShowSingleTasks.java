package io.github.toniidev.toniifarms.commands;

import io.github.toniidev.toniifarms.classes.server.ServerPlayer;
import io.github.toniidev.toniifarms.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowSingleTasks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!CommandUtils.checkPrerequisites(commandSender)) return true;

        Player player = (Player) commandSender;
        ServerPlayer serverPlayer = ServerPlayer.getInstance(player);

        player.openInventory(serverPlayer.getSingleTasksInventory());

        return true;
    }
}
