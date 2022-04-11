package online.bingulhan.autosell.util;

import online.bingulhan.autosell.AutoSell;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class MaterialController {


    //Config'te belirlenen materyalleri sunucuya kaydeder.
    public static void loadMaterials() {
        AutoSell.getInstance().materials=new ArrayList<>();
        AutoSell.getInstance().prices=new HashMap<>();


        for (String material : AutoSell.getInstance().getConfig().getConfigurationSection("items").getKeys(false)) {

            try {

                Material m = Material.valueOf(material);
                double price = AutoSell.getInstance().getConfig().getDouble("items."+material+".sell");

                AutoSell.getInstance().materials.add(m);
                AutoSell.getInstance().prices.put(m, price);



            }catch (Exception exception) {

            }


        }

    }
}
