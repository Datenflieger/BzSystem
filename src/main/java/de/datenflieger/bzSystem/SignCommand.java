package de.datenflieger.bzSystem;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public SignCommand(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.not-a-player"));
            return true;
        }

        if (player.hasPermission("bzsmp.sign.use")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getAmount() == 0) {

                player.sendMessage(IridiumColorAPI.process(plugin.getConfig().getString("messages.prefix") + "§cDu musst ein Item in der Hand halten!"));
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
                return true;
            }

            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

            for (String line : lore) {
                if (line.contains(player.getName())) {

                    player.sendMessage(IridiumColorAPI.process(plugin.getConfig().getString("messages.prefix") + "§cDu hast das Item §cbereits §cSigniert!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
                    return true;
                }
            }

            LuckPerms luckPerms = LuckPermsProvider.get();
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            String prefix = user.getCachedData().getMetaData().getPrefix();

            if (args.length > 0) {
                String text = String.join(" ", args);

                lore.add(IridiumColorAPI.process(text));
            }


            lore.add("§f ");
            lore.add(IridiumColorAPI.process("§7Signiert von " + (prefix != null ? prefix : "") + "§a" + player.getName() + " §7am §e" + new SimpleDateFormat("dd.MM.yyyy").format(new Date())));

            meta.setLore(lore);
            item.setItemMeta(meta);

            player.sendMessage(IridiumColorAPI.process(plugin.getConfig().getString("messages.prefix") + "§7Du hast das Item §aerfolgreich §7Signiert!"));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        }
        return false;
    }
}