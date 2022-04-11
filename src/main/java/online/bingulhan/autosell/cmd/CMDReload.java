package online.bingulhan.autosell.cmd;

import online.bingulhan.autosell.AutoSell;
import online.bingulhan.autosell.util.MaterialController;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (sender.hasPermission("bas.admin")) {

                AutoSell.getInstance().reloadConfig();
                AutoSell.getInstance().saveConfig();
                MaterialController.loadMaterials();


                sender.sendMessage(ChatColor.GREEN+"Eklenti güncellendi.");

            }else {
                sender.sendMessage(ChatColor.RED+"Yetkiniz yok.");
            }


        }
        return true;
    }
}
