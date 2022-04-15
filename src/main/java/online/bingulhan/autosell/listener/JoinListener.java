package online.bingulhan.autosell.listener;

import online.bingulhan.autosell.AutoSell;
import online.bingulhan.autosell.boost.PlayerBoostAccount;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {


    @EventHandler
    public void event(PlayerJoinEvent e) {

        boolean isRegistered=false;


        if (AutoSell.accounts.get(e.getPlayer().getName())!=null) {
            isRegistered=true;

        }
        if (!isRegistered) {

            PlayerBoostAccount account = AutoSell.getInstance().backupManager.getBackup(e.getPlayer().getName());

            AutoSell.accounts.put(e.getPlayer().getName(), account);

        }
    }
}
