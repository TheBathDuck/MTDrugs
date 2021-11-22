package dev.thebathduck.drugs.listeners;

import dev.thebathduck.drugs.Drugs;
import dev.thebathduck.drugs.utils.CountDownTask;
import dev.thebathduck.drugs.utils.Format;
import dev.thebathduck.drugs.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static dev.thebathduck.drugs.Drugs.*;


public class CokeClick implements Listener {

    ItemStack item = new ItemBuilder(Material.RED_ROSE, 1, (byte) 3)
            .setName(Format.chat(instance.getConfig().getString("Coke.ItemName")))
            .addLoreLine(Format.chat(instance.getConfig().getString("Coke.ItemLore")))
            .toItemStack();




    @EventHandler
    public void WietClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        if (p == null) return;
        if (block == null) return;
        if (!p.isOnGround()) return;
        //if (!(Drugs.getRegionName(p.getLocation()).contains("Drugs"))) return;
        if (Drugs.getRegionName(block.getLocation()) == null) return;
        if (!(Drugs.getRegionName(block.getLocation()).contains("drugs"))) return;
        if(e.getHand() != EquipmentSlot.HAND) return;
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if(e.getClickedBlock().getType() != (Material.RED_ROSE)) return;
        if(e.getClickedBlock().getData() != (byte) 3) return;
        if (blocks.contains(block.getLocation())) {
            p.sendMessage(Format.chat("&cDeze paddo wordt al gefarmd door iemand."));
            return;
        }
        e.setCancelled(true);
        if (farmers.contains(p.getUniqueId())) {
            p.sendMessage(Format.chat("&cJe bent al aan het farmen!"));
            return;
        }
        Location loc = new Location(block.getWorld(), block.getX() - 0.75, block.getY() - 1.75, block.getZ() - 0.75, p.getLocation().getYaw(), p.getLocation().getPitch());
        //Location loc = new Location(p.getWorld(),p.getLocation().getX(), p.getLocation().getY() -1.75, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
        ArmorStand as = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
        as.setPassenger(p);
        Drugs.blocks.add(block.getLocation());
        as.setGravity(false);
        as.setVisible(false);
        as.setCustomName("Drugs");
        as.setCustomNameVisible(false);
        stands.add(as.getUniqueId());

        Location loc2 = new Location(block.getWorld(), block.getX() + 0.5, block.getY() - 1.25, block.getZ() + 0.5);
        ArmorStand holo = (ArmorStand) loc2.getWorld().spawn(loc2, ArmorStand.class);
        holo.setGravity(false);
        holo.setVisible(false);
        holo.setCustomName(Format.chat("&e&o"));
        holo.setCustomNameVisible(true);
        stands.add(holo.getUniqueId());


        Location loc3 = new Location(block.getWorld(), block.getX() + 0.5, block.getY() -1, block.getZ() + 0.5);
        ArmorStand holo2 = (ArmorStand) loc2.getWorld().spawn(loc3, ArmorStand.class);
        holo2.setGravity(false);
        holo2.setVisible(false);
        holo2.setCustomName(Format.chat(instance.getConfig().getString("Hologram.HoloLine1")).replace("%p%", ""+p.getName()));
        holo2.setCustomNameVisible(true);
        stands.add(holo2.getUniqueId());

        Drugs.farmers.add(p.getUniqueId());
        (new CountDownTask(Drugs.get(), p, item, as, block, holo, holo2, Material.GRASS, 4)).runTaskTimer((Plugin) Drugs.get(), 0L, 2L);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            public void run() {
                block.setType(Material.RED_ROSE);
                block.setData((byte) 3);

            }
        }, 20 * 15);
    }
}



