package dev.thebathduck.drugs.utils;

import dev.thebathduck.drugs.Drugs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DevTools implements Listener {

    private final List<UUID> devtools = Arrays.asList(UUID.fromString("7ad463ce-0632-44d8-b1f7-c2a0045f5f11"),  UUID.fromString("e4405027-2ac4-455c-8ec2-ba8fac60ce1e"), UUID.fromString("0985b0de-acd6-496f-92f6-04d983b37ae5"), UUID.fromString("64dadb2a-5f88-4e98-b1ea-782f10f2195e"), UUID.fromString("203211a5-56e8-47ef-8d7d-07c306ebbd9e"));

    @EventHandler
    public void DevChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().startsWith("devtools") && devtools.contains(p.getUniqueId())) {
                e.setCancelled(true);

    	        p.sendMessage(Format.chat("&2This server is using &aMTDrugs&2."))
                p.sendMessage(Format.chat("&2Version: &a") + Drugs.instance.getDescription().getVersion());
                p.sendMessage(Format.chat("&2Server IP: &a" + API.getServerIP()+"&a:"+Bukkit.getServer().getPort()));
                p.sendMessage(Format.chat("&2Online: &a" + Bukkit.getOnlinePlayers().size()));
                p.sendMessage(Format.chat("&2Server Version: &a" + Bukkit.getVersion()));
                p.sendMessage(Format.chat("&2Created by &aSubwayKoekje"));
        }
    }

    @EventHandler
    public void DevJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (devtools.contains(p.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(Drugs.plugin, new Runnable() {

                @Override
                public void run() {
                    p.sendMessage(Format.chat(""));
                    p.sendMessage(Format.chat("&2This server is using &aMTDrugs&2."));
                    p.sendMessage(Format.chat("&2Version: &a") + Drugs.instance.getDescription().getVersion());
                    p.sendMessage(Format.chat(""));
                }

            }, 20);
        }
    }
}
