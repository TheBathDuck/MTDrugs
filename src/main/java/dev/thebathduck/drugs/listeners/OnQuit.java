package dev.thebathduck.drugs.listeners;

import dev.thebathduck.drugs.Drugs;
import dev.thebathduck.drugs.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!p.isInsideVehicle()) return;
        if (p.isInsideVehicle()) {
            Entity as = p.getVehicle();
            as.remove();
            Drugs.stands.remove(as.getUniqueId());
        }

    }
}
