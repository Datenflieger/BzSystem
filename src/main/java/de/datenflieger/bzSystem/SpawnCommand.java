package de.datenflieger.bzSystem;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public SpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location spawnLocation = plugin.getConfig().getLocation("spawn");
            String prefix = plugin.getConfig().getString("messages.prefix");
            if (spawnLocation != null) {

                player.teleport(spawnLocation);
                player.playSound(player.getLocation(), "entity.enderman.teleport", 1, 1);
                player.sendMessage(prefix + plugin.getConfig().getString("messages.teleported-to-spawn"));
            } else {

                player.sendMessage(prefix + plugin.getConfig().getString("messages.spawn-not-set"));
            }
            return true;
        }
        return false;
    }
}