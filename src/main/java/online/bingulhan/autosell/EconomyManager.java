package online.bingulhan.autosell;

import org.bukkit.OfflinePlayer;

public interface EconomyManager {

    public void addMoney(OfflinePlayer player, Double amout);
    public void takeMoney(OfflinePlayer player, Double amount);
    public boolean isMoneyPlayer(OfflinePlayer player, Double amount);
}
