package online.bingulhan.autosell.economy;

import net.milkbowl.vault.economy.Economy;
import online.bingulhan.autosell.AutoSell;
import online.bingulhan.autosell.EconomyManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomyManager implements EconomyManager {


    Economy economy = null;

    public VaultEconomyManager() {
        if (AutoSell.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = AutoSell.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        economy = rsp.getProvider();

    }


    @Override
    public void addMoney(OfflinePlayer player, Double amout) {

        economy.depositPlayer(player, amout);

    }

    @Override
    public void takeMoney(OfflinePlayer player, Double amount) {

        economy.withdrawPlayer(player, amount);


    }

    @Override
    public boolean isMoneyPlayer(OfflinePlayer player, Double amount) {
        if (economy.getBalance(player)>=amount) {
            return true;

        }
        return false;
    }
}
