package de.datenflieger.bzSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StarterKitCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public StarterKitCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = plugin.getConfig().getString("messages.prefix");

        if (!(sender instanceof Player)) {

            sender.sendMessage(prefix + plugin.getConfig().getString("messages.not-a-player"));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("starterkit.use")) {

            player.sendMessage(prefix + plugin.getConfig().getString("messages.no-permission"));
            return true;
        }

        ((BzSystem) plugin).giveStarterKit(player);
        player.sendMessage(prefix + plugin.getConfig().getString("messages.starter-kit-received"));
        return true;
    }
}