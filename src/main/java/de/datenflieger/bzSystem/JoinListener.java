package de.datenflieger.bzSystem;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener implements Listener {
    private final JavaPlugin plugin;

    public JoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String prefix = plugin.getConfig().getString("messages.prefix");
        String joinMessage = plugin.getConfig().getString("messages.join").replace("%player%", playerName);

        event.setJoinMessage(joinMessage);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        event.getPlayer().sendTitle(
            IridiumColorAPI.process(plugin.getConfig().getString("messages.join-title").replace("%player%", playerName)),
            IridiumColorAPI.process(plugin.getConfig().getString("messages.join-subtitle")),
            20, 60, 10
        );

        if (!event.getPlayer().hasPlayedBefore()) {
            ((BzSystem) plugin).giveStarterKit(event.getPlayer());
        }
    }
}