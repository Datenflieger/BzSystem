package de.datenflieger.bzSystem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class BzSystem extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        this.getCommand("sign").setExecutor(new SignCommand(this));
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getCommand("starterkit").setExecutor(new StarterKitCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public void giveStarterKit(Player player) {
        ConfigurationSection starterKitSection = getConfig().getConfigurationSection("starter-kit");
        if (starterKitSection == null) {

            getLogger().warning("Starter kit section is missing in the config!");
            return;
        }

        ConfigurationSection itemsSection = starterKitSection.getConfigurationSection("items");
        if (itemsSection == null) {

            getLogger().warning("Items section is missing in the starter kit config!");
            return;
        }

        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
            if (itemSection != null) {
                ItemStack item = new ItemStack(
                        Material.valueOf(itemSection.getString("type")),
                        itemSection.getInt("amount")
                );
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(itemSection.getString("name"));
                    meta.setLore(itemSection.getStringList("lore"));
                    item.setItemMeta(meta);
                }
                player.getInventory().setItem(itemSection.getInt("slot"), item);
            }
        }

        ConfigurationSection armorSection = starterKitSection.getConfigurationSection("armor");
        if (armorSection != null) {
            giveArmor(player, armorSection, "helmet", 39);
            giveArmor(player, armorSection, "chestplate", 38);
            giveArmor(player, armorSection, "leggings", 37);
            giveArmor(player, armorSection, "boots", 36);
        }
    }

    private void giveArmor(Player player, ConfigurationSection armorSection, String armorType, int slot) {
        ConfigurationSection armorItemSection = armorSection.getConfigurationSection(armorType);
        if (armorItemSection != null) {
            ItemStack armorItem = new ItemStack(
                    Material.valueOf(armorItemSection.getString("type")),
                    1
            );
            ItemMeta meta = armorItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(armorItemSection.getString("name"));
                meta.setLore(armorItemSection.getStringList("lore"));
                armorItem.setItemMeta(meta);
            }
            player.getInventory().setItem(slot, armorItem);
        }
    }
}