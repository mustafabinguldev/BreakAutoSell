package online.bingulhan.autosell;

import online.bingulhan.autosell.backup.YamlBackupManager;
import online.bingulhan.autosell.boost.Boost;
import online.bingulhan.autosell.boost.PlayerBoostAccount;
import online.bingulhan.autosell.cmd.CMDAutoSell;
import online.bingulhan.autosell.cmd.CMDItems;
import online.bingulhan.autosell.economy.VaultEconomyManager;
import online.bingulhan.autosell.listener.BlockListener;
import online.bingulhan.autosell.listener.JoinListener;
import online.bingulhan.autosell.util.MaterialController;
import online.bingulhan.autosell.util.Timer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class AutoSell extends JavaPlugin {


    /*

       Yapımcı BingulHan'dır. Eklenti geliştirilebilir.



     */
    private static AutoSell instance;
    public VaultEconomyManager economyManager;

    public ArrayList<Material> materials = new ArrayList<>();
    public HashMap<Material, Double> prices = new HashMap<>();

    public static HashMap<String, Boolean> isBreaking = new HashMap<>();
    public static HashMap<String, Double> fb = new HashMap<>();
    public static HashMap<String, Integer> kb = new HashMap<>();

    public static HashMap<String, PlayerBoostAccount> accounts = new HashMap<>();

    public ArrayList<Boost> globalBoosts =new ArrayList<>();

    public IBackupManager backupManager;


    @Override
    public void onEnable() {
        instance=this;

        getConfig().options().copyDefaults(true);
        saveConfig();


        //Depend ekonomiyi algılar.

        if (getConfig().getString("depend").equalsIgnoreCase("Vault")) {
            economyManager=new VaultEconomyManager();
        }

        if (economyManager==null) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED+"Depend eklenti bulunmadi.");
            getServer().getPluginManager().disablePlugin(this);
        }


        //Materyalleri yükler.
        MaterialController.loadMaterials();



        //Kontrol amaçlı girilmiştir.
        getInstance().getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"Dev: BingulHan: ");



        //Eklentideki sistem bu listenerde bulunur.

        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);


        getCommand("asf").setExecutor(new CMDItems());
        getCommand("autosell").setExecutor(new CMDAutoSell());
        getCommand("otosat").setExecutor(new CMDAutoSell());
        getCommand("os").setExecutor(new CMDAutoSell());
        getCommand("as").setExecutor(new CMDAutoSell());



        //Gui için event.
        AutoSell.getInstance().getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void event(InventoryClickEvent e) {
                if (e.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.RED+"Eşya Satış Fiyatları")) e.setCancelled(true);
            }
        }, this);


        AutoSell.getInstance().getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void event(PlayerJoinEvent e) {
                isBreaking.put(e.getPlayer().getName(), false);
                fb.put(e.getPlayer().getName(), 0.0);
                kb.put(e.getPlayer().getName(), 0);

            }
        }, this);

        AutoSell.getInstance().getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void event(PlayerQuitEvent e) {
                isBreaking.remove(e.getPlayer().getName());
                fb.remove(e.getPlayer().getName());
                kb.remove(e.getPlayer().getName());

            }
        }, this);


        for (OfflinePlayer player : getServer().getOnlinePlayers()) {
            isBreaking.put(player.getName(), false);
            fb.put(player.getName(), 0.0);
            kb.put(player.getName(), 0);
        }

        backupManager=new YamlBackupManager();


        Timer.init();

    }

    @Override
    public void onDisable() {

        Set<String> list = accounts.keySet();
        for (String c : list) {

            PlayerBoostAccount account = accounts.get(c);
            backupManager.backup(account);


        }
    }

    public static AutoSell getInstance() {
        return instance;
    }
}
