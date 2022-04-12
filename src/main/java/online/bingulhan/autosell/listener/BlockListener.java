package online.bingulhan.autosell.listener;

import com.cryptomorin.xseries.messages.ActionBar;
import online.bingulhan.autosell.AutoSell;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BlockListener implements Listener {

    public void giving(OfflinePlayer player) {

        if (!AutoSell.isBreaking.get(player.getName())) {
            AutoSell.getInstance().economyManager.addMoney(player, AutoSell.fb.get(player.getName()));
            AutoSell.getInstance().fb.replace(player.getName(), 0.0);
            AutoSell.kb.replace(player.getName(), 0);
        }
    }
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

                int d = 0;
                for (ItemStack xx : e.getBlock().getDrops()) {
                    d=xx.getAmount()+d;
                }

                Double price = AutoSell.getInstance().prices.get(m)*Double.parseDouble(""+d);
                double boost = 0;

                //Çarpanları tespit eder.
                for (String carpan : AutoSell.getInstance().getConfig().getConfigurationSection("carpan").getKeys(false)) {
                    if (e.getPlayer().hasPermission(carpan)) {
                        int c = AutoSell.getInstance().getConfig().getInt("carpan."+carpan);
                        boost=boost+c;

                    }
                }

                //Boost price yansıması.
                double mc = price /100;
                double mp = mc*boost;

                price = price +mp;


                Double i = AutoSell.fb.get(e.getPlayer().getName());
                AutoSell.fb.replace(e.getPlayer().getName(), price+i);
                AutoSell.isBreaking.replace(e.getPlayer().getName(), true);


                //Double değeri daha güzel bir görüntüye kavuşturur.
                DecimalFormat formatter = new DecimalFormat("##.###");

                boost=boost+100;
                boost=boost/100;

                Double main = AutoSell.getInstance().prices.get(m);

                Double j = AutoSell.fb.get(e.getPlayer().getName());

                //Oyuncuya gönderilecek mesaj ayarlanır.
                Player p = e.getPlayer();
                String s = AutoSell.getInstance().getConfig().getString("actionbar-message");
                s = StringUtils.replace(s, "%kazanc%", formatter.format(j));
                s = StringUtils.replace(s, "%carpan%", boost+"X");
                s = StringUtils.replace(s, "%maden%", ""+AutoSell.kb.get(e.getPlayer().getName()));
                s = ChatColor.translateAlternateColorCodes('&', s);


                e.getBlock().setType(Material.AIR);

                AutoSell.kb.replace(e.getPlayer().getName(), AutoSell.kb.get(e.getPlayer().getName())+d);



                //XMaterial'in ActionBar API kullanılarak oyuncuya mesaj gönderilir.
                ActionBar.sendActionBar(p, s);


                AutoSell.getInstance().getServer().getScheduler().runTaskLater(AutoSell.getInstance(), () -> {
                    AutoSell.isBreaking.replace(e.getPlayer().getName(), false);
                }, 100);

                AutoSell.getInstance().getServer().getScheduler().runTaskLater(AutoSell.getInstance(), () -> {
                    giving(e.getPlayer());
                }, 120);

                return;
            }
        }

    }
}
