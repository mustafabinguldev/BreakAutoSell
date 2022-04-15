package online.bingulhan.autosell.backup;

import online.bingulhan.autosell.AutoSell;
import online.bingulhan.autosell.IBackupManager;
import online.bingulhan.autosell.boost.Boost;
import online.bingulhan.autosell.boost.PlayerBoostAccount;
import online.bingulhan.autosell.util.FileUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class YamlBackupManager implements IBackupManager {

    File backupFolder = new File(AutoSell.getInstance().getDataFolder(), "backups");

    public YamlBackupManager() {
        if (!backupFolder.exists()) {
            backupFolder.mkdir();
        }

    }
    @Override
    public void backup(PlayerBoostAccount account) {
        File file = new File(backupFolder.getPath(), account.ownerName+".yml");
        if (file.exists()) {
            FileUtil.delete(file);
        }

        if (account.boosts.size()>0) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (Boost boost : account.boosts) {
                int id = account.boosts.indexOf(boost);
                config.set("boosts."+"id"+id+".value", boost.value);
                config.set("boosts."+"id"+id+".last", boost.second);
                config.set("boosts."+"id"+id+".of", boost.isOfflineActive);
            }
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public PlayerBoostAccount getBackup(String playerName) {

        File file = new File(backupFolder.getPath(), playerName+".yml");

        PlayerBoostAccount account=null;

        if (!file.exists()) {

            account= new PlayerBoostAccount(playerName);


        }else {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Boost> blist=  new ArrayList<>();
            FileConfiguration c = YamlConfiguration.loadConfiguration(file);

            if (c.getConfigurationSection("boosts").getKeys(false).size()>0) {
                for (String boosts : c.getConfigurationSection("boosts").getKeys(false)) {

                    int value = c.getInt("boosts."+boosts+".value");
                    int lastSecond = c.getInt("boosts."+boosts+".last");
                    Boolean isOffline = c.getBoolean("boosts."+boosts+".of");

                    Boost boost = new Boost(value, isOffline, lastSecond);
                    blist.add(boost);
                }
            }

            account = new PlayerBoostAccount(blist, playerName);

        }
        return account;
    }

    @Override
    public ArrayList<PlayerBoostAccount> getAll() {
        return null;
    }
}
