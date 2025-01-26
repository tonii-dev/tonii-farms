package io.github.toniidev.toniifarms.classes.tasks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GameTask {
    public abstract boolean canComplete(Player player);
    public abstract void complete(Player player);
    public abstract ItemStack getIcon(Player player);
}
