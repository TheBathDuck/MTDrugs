package dev.thebathduck.drugs;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.thebathduck.drugs.command.MTDrugsCMD;
import dev.thebathduck.drugs.listeners.*;
import dev.thebathduck.drugs.utils.DevTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class Drugs extends JavaPlugin {

    public static ArrayList<UUID> farmers = new ArrayList<>();
    public static ArrayList<Location> blocks = new ArrayList<>();
    public static ArrayList<UUID> stands = new ArrayList<>();
    public static Plugin plugin = null;
    public static Drugs instance;
    public static Drugs get() {
        return (Drugs) plugin;
    }

    private static WorldGuardPlugin worldGuard;

    static {
        Plugin worldGuardPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (worldGuardPlugin != null && worldGuardPlugin instanceof WorldGuardPlugin)
            worldGuard = (WorldGuardPlugin)worldGuardPlugin;
    }



    public static boolean plotExists(Location plotLoc) {
        return (worldGuard.getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc).getRegions().size() != 0);
    }

    public static String getRegionName(Location plotLoc) {
        if (plotExists(plotLoc))
            return ((ProtectedRegion)worldGuard.getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc).getRegions().iterator().next()).getId();
        return null;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();


        instance = this;
        plugin = this;
        getCommand("mtdrugs").setExecutor(new MTDrugsCMD());
        Bukkit.getPluginManager().registerEvents(new PaddoClick(), this);
        Bukkit.getPluginManager().registerEvents(new WietClick(), this);
        Bukkit.getPluginManager().registerEvents(new OnQuit(), this);
        Bukkit.getPluginManager().registerEvents(new OpiumClick(), this);
        Bukkit.getPluginManager().registerEvents(new CokeClick(), this);
        Bukkit.getPluginManager().registerEvents(new DevTools(), this);
        //Bukkit.getPluginManager().registerEvents(new onLeave(), this);
    }

    @Override
    public void onDisable() {
        for(Player x : Bukkit.getOnlinePlayers()) {
            Player p = x.getPlayer();
            World w = p.getWorld();
            for (Entity ent : w.getEntities()) {
                if (ent == null) return;
                if (stands.contains(ent.getUniqueId())) {
                    ent.remove();
                    stands.remove(ent.getUniqueId());
                }
            }
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (!(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

}
