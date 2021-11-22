package dev.thebathduck.drugs.command;

import dev.thebathduck.drugs.Drugs;
import dev.thebathduck.drugs.utils.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MTDrugsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(args.length == 0) {
            p.sendMessage(Format.chat("&2Deze server maakt gebruik van &aMTDrugs&2."));
            p.sendMessage(Format.chat("&2Author: &aSubwayKoekje&2."));
            p.sendMessage(Format.chat("&2Contributers: &aOmeWillem&2, &aMilan_V&2, &aAfger0nd&2, &aGamerJoep_&2."));
            p.sendMessage(Format.chat("&2Versie: &a" + Drugs.instance.getDescription().getVersion() + "&2."));
        } else {
            if (args[0].equals("reload")) {
                if (p.hasPermission("mtdrugs.use")) {
                    Drugs.instance.reloadConfig();
                    p.sendMessage(Format.chat("&aConfig reloaded, bekijk je console voor enkele errors."));
                } else {
                    p.sendMessage(Format.chat("&cJij kan dit commando niet uitvoeren."));
                }

            }
            if (args[0].equals("help")) {
                if (p.hasPermission("mtdrugs.use")) {
                    Drugs.instance.reloadConfig();
                    p.sendMessage(Format.chat("&2/mtdrugs reload &7- &aHerlaad de configuratie."));
                } else {
                    p.sendMessage(Format.chat("&cJij kan dit commando niet uitvoeren."));
                }

            }

        }
        return false;
    }
}
