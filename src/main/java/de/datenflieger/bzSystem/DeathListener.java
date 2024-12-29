package de.datenflieger.bzSystem;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathListener implements Listener {
    private final JavaPlugin plugin;

    public DeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        String prefix = plugin.getConfig().getString("death-messages.prefix");
        String deathMessage = plugin.getConfig().getString("death-messages." + event.getEntity().getLastDamageCause().getCause().toString());
        if (deathMessage != null) {
            deathMessage = prefix + deathMessage.replace("%player%", event.getEntity().getName());

            event.setDeathMessage(deathMessage);
        }
        Bukkit.getOnlinePlayers().forEach(player ->
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0f, 1.0f)
        );
    }
}