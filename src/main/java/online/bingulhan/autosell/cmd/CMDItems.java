package online.bingulhan.autosell.cmd;

import online.bingulhan.autosell.AutoSell;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CMDItems implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (AutoSell.getInstance().getConfig().getBoolean("info-gui")) {

                Inventory gui = Bukkit.createInventory(null, 27, ChatColor.RED+"Eşya Satış Fiyatları");

                for (Material material : AutoSell.getInstance().materials) {

                    ItemStack item = new ItemStack(material);
                    ItemMeta meta = item.getItemMeta();
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(" ");
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&7Adet Satış Fiyatı: &e"+AutoSell.getInstance().prices.get(material)));
                    lore.add("  ");

                    meta.setLore(lore);

                    item.setItemMeta(meta);

                    gui.addItem(item);




                }

                ((Player) sender).closeInventory();

                AutoSell.getInstance().getServer().getScheduler().runTaskLater(AutoSell.getInstance(), () -> {
                    ((Player) sender).openInventory(gui);
                }, 1);


            }

        }


        return true;
    }
}
