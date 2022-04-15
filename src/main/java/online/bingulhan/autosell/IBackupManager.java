package online.bingulhan.autosell;

import online.bingulhan.autosell.boost.PlayerBoostAccount;

import java.util.ArrayList;

public interface IBackupManager {

    public void backup(PlayerBoostAccount account);
    public PlayerBoostAccount getBackup(String playerName);
    public ArrayList<PlayerBoostAccount> getAll();

}
