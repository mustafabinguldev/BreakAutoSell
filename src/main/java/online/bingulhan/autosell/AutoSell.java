package online.bingulhan.autosell;

import online.bingulhan.autosell.cmd.CMDItems;
import online.bingulhan.autosell.cmd.CMDReload;
import online.bingulhan.autosell.economy.VaultEconomyManager;
import online.bingulhan.autosell.listener.BlockListener;
import online.bingulhan.autosell.util.MaterialController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class AutoSell extends JavaPlugin {


    /*

       Yapımcı BingulHan'dır. Eklenti geliştirilebilir.



     */
    private static AutoSell instance;
    public VaultEconomyManager economyManager;

    public ArrayList<Material> materials = new ArrayList<>();
    public HashMap<Material, Double> prices = new HashMap<>();

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
        getInstance().getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"Dev: BingulHan | Item Sayisi: "+materials.size());



        //Eklentideki sistem bu listenerde bulunur.

        getServer().getPluginManager().registerEvents(new BlockListener(), this);


        getCommand("asf").setExecutor(new CMDItems());
        getCommand("basreload").setExecutor(new CMDReload());


        //Gui için event.
        AutoSell.getInstance().getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void event(InventoryClickEvent e) {
                if (e.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.RED+"Eşya Satış Fiyatları")) e.setCancelled(true);
            }
        }, this);
    }

    @Override
    public void onDisable() {
    }

    public static AutoSell getInstance() {
        return instance;
    }
}
