package dev.thebathduck.drugs.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thebathduck.drugs.Drugs;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {

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
