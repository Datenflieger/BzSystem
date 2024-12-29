package de.datenflieger.bzSystem;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetSpawnCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetSpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            plugin.getConfig().set("spawn", location);
            plugin.saveConfig();
            String prefix = plugin.getConfig().getString("messages.prefix");
            player.sendMessage(prefix + plugin.getConfig().getString("messages.spawn-set"));
            return true;
        }
        return false;
    }
}