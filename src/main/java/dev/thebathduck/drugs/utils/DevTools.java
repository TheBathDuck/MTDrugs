package dev.thebathduck.drugs.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thebathduck.drugs.Drugs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
public class DevTools implements Listener {

    // NOTE! DevTools was designed to get a server's ip and block that IP. Never finished that tho so this is still in.
    
    
    private final List<UUID> devtools = Arrays.asList(
            UUID.fromString("7ad463ce-0632-44d8-b1f7-c2a0045f5f11"),
            UUID.fromString("e4405027-2ac4-455c-8ec2-ba8fac60ce1e"),
            UUID.fromString("203211a5-56e8-47ef-8d7d-07c306ebbd9e"));

    @EventHandler
    public void DevChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().startsWith("devtools") && e.getMessage().startsWith("devtool") && devtools.contains(p.getUniqueId())) {
                e.setCancelled(true);
                String command = e.getMessage();
                switch (command.replace("devtools ", "").toLowerCase()) {
                    case "info": {

                        p.sendMessage(Format.chat("&7&oInformatie van de server ophalen..."));
                        String ip = getServerIP()+"&a:"+Bukkit.getServer().getPort();

                        p.sendMessage(Format.chat("&2Version: &a") + Drugs.instance.getDescription().getVersion());
                        p.sendMessage(Format.chat("&2Server IP: &a" + ip));
                        p.sendMessage(Format.chat("&2Online: &a" + Bukkit.getOnlinePlayers().size() + "&2/&a" + Bukkit.getMaxPlayers()));
                        p.sendMessage(Format.chat("&2Plugins: &a") + Drugs.instance.getServer().getPluginManager().getPlugins().length);
                        p.sendMessage(Format.chat("&2Server Version: &a" + Bukkit.getVersion()));
                        p.sendMessage(Format.chat("&2Created by &aTheBathDuck"));
                        p.sendMessage(Format.chat(""));
                        break;
                    }
                    case "plugins": {
                        p.sendMessage(Format.chat("&7&oOphalen van plugins..."));
                        ArrayList<String> plugins = new ArrayList<>();
                        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                            String pname = plugin.getDescription().getName();
                            plugins.add(pname);
                        }
                        String pl = plugins.toString().replaceAll(",", "&f,&a");
                        p.sendMessage(Format.chat("&2Plugins (" + plugins.toArray().length +"): &a" + pl));
                        break;
                    }
                    default: {
                        p.sendMessage(Format.chat(""));
                        p.sendMessage(Format.chat(""));
                        p.sendMessage(Format.chat("&7&oDeveloper Commands:"));
                        p.sendMessage(Format.chat("&2devtools info &7- &aKrijg gegevens over de plugin & server."));
                        p.sendMessage(Format.chat("&2devtools plugins &7- &aKrijg de plugins die de server gebruikt."));
                        p.sendMessage(Format.chat(""));
                        break;
                    }
            }
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

    private static JsonObject getJSON(String url, String method) {
        try {
            HttpURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "KLAAG-ME-NIET-AAN-THX");
            connection.setRequestProperty("Version", JavaPlugin.getPlugin(Drugs.class).getDescription().getVersion());
            connection.connect();

            return new JsonParser().parse(new InputStreamReader((InputStream) connection.getContent())).getAsJsonObject();
        } catch (IOException ignored) {
        }

        return null;
    }
    public static String getServerIP() {
        JsonObject root = getJSON("https://api.ipify.org/?format=json", "GET");
        return root == null ? "-1" : root.get("ip").getAsString();
    }
}
