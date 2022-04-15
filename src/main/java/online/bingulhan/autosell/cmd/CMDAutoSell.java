package online.bingulhan.autosell.cmd;

import online.bingulhan.autosell.AutoSell;
import online.bingulhan.autosell.boost.Boost;
import online.bingulhan.autosell.util.MaterialController;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CMDAutoSell implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length>0) {
            if (args[0].equals("boost")) {
                if (args.length>1) {
                    String playerName = args[1];
                    OfflinePlayer p = AutoSell.getInstance().getServer().getOfflinePlayer(playerName);
                    if (p.isOnline()) {
                        if (args.length>2) {
                            try {
                                int value = Integer.parseInt(args[2]);
                                if (args.length>3) {
                                    if (args[3].equalsIgnoreCase("s") || args[3].equalsIgnoreCase("second")) {
                                        if (args.length>4) {
                                            int time = Integer.parseInt(args[4]);

                                            Boost boost = new Boost(value, false, time);
                                            AutoSell.getInstance().accounts.get(playerName).boosts.add(boost);

                                            Double v= Double.valueOf(value/100);

                                            sender.sendMessage(ChatColor.GREEN+playerName+" Oyuncusuna "+time+" saniye "+v+"x"+" boost eklendi.");
                                        }else {
                                            sender.sendMessage(ChatColor.RED+"Süre giriniz.");
                                        }
                                    }

                                    if (args[3].equalsIgnoreCase("m") || args[3].equalsIgnoreCase("minute")) {
                                        if (args.length>4) {
                                            int time = Integer.parseInt(args[4]);

                                            time=time*60;

                                            Boost boost = new Boost(value, false, time);
                                            AutoSell.getInstance().accounts.get(playerName).boosts.add(boost);

                                            Double v= Double.valueOf(value/100);

                                            time=time/60;

                                            sender.sendMessage(ChatColor.GREEN+playerName+" Oyuncusuna "+time+" dakika "+v+"x"+" boost eklendi.");
                                        }else {
                                            sender.sendMessage(ChatColor.RED+"Süre giriniz.");
                                        }
                                    }

                                    if (args[3].equalsIgnoreCase("h") || args[3].equalsIgnoreCase("hour")) {
                                        if (args.length>4) {
                                            int time = Integer.parseInt(args[4]);

                                            time=time*60*60;

                                            Boost boost = new Boost(value, false, time);
                                            AutoSell.getInstance().accounts.get(playerName).boosts.add(boost);

                                            Double v= Double.valueOf(value/100);

                                            time=time/60;

                                            sender.sendMessage(ChatColor.GREEN+playerName+" Oyuncusuna "+time+" saat "+v+"x"+" boost eklendi.");
                                        }else {
                                            sender.sendMessage(ChatColor.RED+"Süre giriniz.");
                                        }
                                    }


                                }else {

                                }
                            }catch (Exception exception) {

                            }
                        }

                    }else {
                        sender.sendMessage(ChatColor.RED+playerName+" aktif degil.");
                    }
                }
            }

            if (args[0].equals("reload")) {
                MaterialController.loadMaterials();

                sender.sendMessage(ChatColor.GREEN+"Eklenti güncellendi.");



            }
            if (args[0].equals("globalboost")) {
                if (args.length>1) {
                        int value = Integer.parseInt(args[1]);
                        if (args.length>2) {
                            if (args[2].equalsIgnoreCase("s") || args[2].equalsIgnoreCase("second")) {
                                if (args.length>3) {
                                    int time = Integer.parseInt(args[3]);

                                    Boost boost = new Boost(value, true, time);
                                    AutoSell.getInstance().globalBoosts.add(boost);

                                    Double v= Double.valueOf(value/100);

                                    sender.sendMessage(ChatColor.GREEN+"Global bir şekilde "+time+" saniye "+v+"x"+" boost eklendi.");
                                }else {
                                    sender.sendMessage(ChatColor.RED+"Süre giriniz.");
                                }
                            }

                            if (args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("minute")) {
                                if (args.length>3) {
                                    int time = Integer.parseInt(args[3]);

                                    time=time*60;

                                    Boost boost = new Boost(value, true, time);
                                    AutoSell.getInstance().globalBoosts.add(boost);

                                    Double v= Double.valueOf(value/100);

                                    time=time/60;

                                    sender.sendMessage(ChatColor.GREEN+"Global bir şekilde "+time+" dakika "+v+"x"+" boost eklendi.");
                                }else {
                                    sender.sendMessage(ChatColor.RED+"Süre giriniz.");
                                }
                            }

                            if (args[2].equalsIgnoreCase("h") || args[2].equalsIgnoreCase("hour")) {
                                if (args.length>3) {
                                    int time = Integer.parseInt(args[3]);

                                    time=time*60*60;

                                    Boost boost = new Boost(value, true, time);
                                    AutoSell.getInstance().globalBoosts.add(boost);

                                    Double v= Double.valueOf(value/100);

                                    time=time/60;

                                    sender.sendMessage(ChatColor.GREEN+" Global bir şekilde "+time+" saat "+v+"x"+" boost eklendi.");
                                }else {
                                    sender.sendMessage(ChatColor.RED+"Süre giriniz.");
                                }
                            }


                        }else {

                        }

                }
            }

        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> l = new ArrayList<>();

        if (args.length==1) {
            l.add("boost");
            l.add("globalboost");
            l.add("reload");
        }

        if (args.length==2) {

            if (args[0].equals("boost")) {
                for (OfflinePlayer p : AutoSell.getInstance().getServer().getOnlinePlayers()) {
                    l.add(p.getName());

                }
            }
            if (args[0].equals("globalboost")) {
                l.add("1000");
                l.add("10");
                l.add("100");
            }
        }

        if (args.length==3) {

            if (args[0].equals("boost")) {
                l.add("1000");
                l.add("10");
                l.add("100");
            }
            if (args[0].equals("globalboost")) {
                l.add("s");
                l.add("m");
                l.add("h");
            }

        }

        if (args.length==4) {
            if (args[0].equals("boost")) {
                l.add("s");
                l.add("m");
                l.add("h");
            }

            if (args[0].equals("globalboost")) {
                l.add("1");
                l.add("15");
                l.add("30");
                l.add("60");
            }
        }

        if (args.length==5) {
            l.add("1");
            l.add("15");
            l.add("30");
            l.add("60");
        }

        return l;
    }
}
