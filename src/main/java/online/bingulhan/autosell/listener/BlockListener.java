package online.bingulhan.autosell.listener;

import com.cryptomorin.xseries.messages.ActionBar;
import online.bingulhan.autosell.AutoSell;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BlockListener implements Listener {

    @EventHandler
    public void event(BlockBreakEvent e) {
        Material m = e.getBlock().getType();

        //Dünyanın otomatik satışa açık mı değil mi kontrol eder.
        boolean isActiveWorld= false;
        ArrayList<String> worlds = (ArrayList<String>) AutoSell.getInstance().getConfig().get("enable-worlds");
        for (String worldname: worlds) {

            if (worldname.equalsIgnoreCase(e.getBlock().getWorld().getName())) {
                isActiveWorld=true;
            }

        }

        //Açık değilse kod bloğu durur.
        if (!isActiveWorld) return;


        //Materyal sorgular.
        for (Material x : AutoSell.getInstance().materials) {

            if (x.toString().equals(m.toString())) {


                //Kırılan eşyanın saf fiyatını tespit eder.

                Double price = AutoSell.getInstance().prices.get(m);
                int boost = 0;


                //Çarpanları tespit eder.
                for (String carpan : AutoSell.getInstance().getConfig().getConfigurationSection("carpan").getKeys(false)) {
                    if (e.getPlayer().hasPermission(carpan)) {
                        int c = AutoSell.getInstance().getConfig().getInt("carpan."+carpan);
                        double p = c*(price/100);
                        price = price + p;
                        boost=boost+c;


                    }
                }


                //Ekonomi Düzenleyicisi ile oyuncuya para akışı sağlanır.
                AutoSell.getInstance().economyManager.addMoney(e.getPlayer(), price);

                //Double değeri daha güzel bir görüntüye kavuşturur.
                DecimalFormat formatter = new DecimalFormat("##.###");


                boost=boost/10;
                //Oyuncuya gönderilecek mesaj ayarlanır.
                Player p = e.getPlayer();
                String s = AutoSell.getInstance().getConfig().getString("actionbar-message");
                s = StringUtils.replace(s, "%kazanc%", formatter.format(price));
                s = StringUtils.replace(s, "%carpan%", "1."+boost+"x");
                s = ChatColor.translateAlternateColorCodes('&', s);

                //Blok Kırılması engellenir yerine hava koyar
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);


                //XMaterial'in ActionBar API kullanılarak oyuncuya mesaj gönderilir.
                ActionBar.sendActionBar(p, s);

                return;
            }
        }

    }
}
