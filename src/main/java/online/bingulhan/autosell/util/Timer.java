package online.bingulhan.autosell.util;

import online.bingulhan.autosell.AutoSell;
import online.bingulhan.autosell.boost.Boost;
import online.bingulhan.autosell.boost.PlayerBoostAccount;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Set;

public class Timer {

    public static void init() {

        AutoSell.getInstance().getServer().getScheduler().runTaskLater(AutoSell.getInstance(), () -> {

            try {
                Set<String> accountsNameList = AutoSell.accounts.keySet();

                for (String k : accountsNameList) {
                    PlayerBoostAccount account = AutoSell.accounts.get(k);
                    for (Boost boost : account.boosts) {

                        if (boost.isOfflineActive) {
                            boost.second--;
                            try {
                                if (!boost.control()){
                                    account.boosts.remove(boost);
                                }
                            }catch (Exception exception) {

                            }
                        }else {
                            OfflinePlayer p = AutoSell.getInstance().getServer().getOfflinePlayer(k);
                            if (p.isOnline()) {
                                boost.second--;


                                try {
                                    if (!boost.control()){
                                        account.boosts.remove(boost);
                                        p.getPlayer().sendMessage(ChatColor.RED+"Bir boostunuzun süresi doldu!");
                                    }
                                }catch (Exception exception) {

                                }
                            }
                        }
                    }
                }

                for (Boost boost : AutoSell.getInstance().globalBoosts) {
                    try {

                        boost.second--;
                        if (!boost.control()) {
                            AutoSell.getInstance().globalBoosts.remove(boost);
                            AutoSell.getInstance().getServer().broadcastMessage(ChatColor.RED+"Bir global boostun süresi doldu.");

                        }
                    }catch (Exception exception) {

                    }
                }
            }catch (Exception exception) {

            }
            AutoSell.getInstance().getServer().getScheduler().runTaskLater(AutoSell.getInstance(), () -> {

                init();

            }, 5);
        }, 25);

    }
}
