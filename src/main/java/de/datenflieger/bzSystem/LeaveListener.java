package de.datenflieger.bzSystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LeaveListener implements Listener {
    private final JavaPlugin plugin;

    public LeaveListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        String prefix = plugin.getConfig().getString("messages.prefix");
        String leaveMessage = plugin.getConfig().getString("messages.leave").replace("%player%", playerName);

        event.setQuitMessage(leaveMessage);
    }
}