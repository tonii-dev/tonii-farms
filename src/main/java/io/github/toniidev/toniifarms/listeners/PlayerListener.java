package io.github.toniidev.toniifarms.listeners;

import io.github.toniidev.toniifarms.classes.server.ServerPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {
    private final Plugin main;

    public PlayerListener(Plugin plugin){
        this.main = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ServerPlayer.registerPlayer(e.getPlayer(), main);
    }
}
