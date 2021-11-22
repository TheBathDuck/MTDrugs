package dev.thebathduck.drugs.utils;

import dev.thebathduck.drugs.Drugs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CountDownTask extends BukkitRunnable {

    private int time;

    private int seconds;
    private Drugs plugin;
    private ItemStack item;
    private Player player;
    private ArmorStand as;
    private ArmorStand as2;
    private ArmorStand as3;
    private Block block;
    private Material mat;
    private int sec;


    //public CountDownTask(Drugs plugin, Player player, ItemStack item, Block block, ArmorStand as2, ArmorStand as3, Material mat, Integer sec) {
    public CountDownTask(Drugs plugin, Player player, ItemStack item, ArmorStand as, Block block, ArmorStand as2, ArmorStand as3, Material mat, Integer sec) {
        this.plugin = plugin;
        this.time = 9;
        this.seconds = 8 - 1;
        this.player = player;
        this.block = block;
        this.item = item;
        this.as = as;
        this.as2 = as2;
        this.as3 = as3;
        this.mat = mat;
        this.sec = sec;
    }

    public void run() {
        this.time--;
        if (this.time == -1) {
            this.time = 9;
            this.seconds--;
        }
        if (this.seconds != -1) {
            if (!player.isInsideVehicle()) {
                //player.sendTitle(Format.chat(""), Format.chat("&cGeannuleerd."), 0, 100, 0);
                if (mat == Material.LONG_GRASS) {
                    block.setType(Material.LONG_GRASS);
                    block.setData((byte) 2);}
                if (mat == Material.RED_ROSE) {
                    block.setType(Material.RED_ROSE);
                    block.setData((byte) 2);
                }
                if (mat == Material.BROWN_MUSHROOM) {
                    block.setType(Material.BROWN_MUSHROOM);
                }
                if (mat == Material.GRASS) {
                    block.setType(Material.RED_ROSE);
                    block.setData((byte) 3);
                }
                Drugs.farmers.remove(player.getUniqueId());
                Drugs.blocks.remove(block.getLocation());
                Drugs.stands.remove(as.getUniqueId());
                Drugs.stands.remove(as2.getUniqueId());
                Drugs.stands.remove(as3.getUniqueId());
                as.remove();
                as3.remove();
                as2.setCustomName(Format.chat(Drugs.instance.getConfig().getString("Hologram.Gestopt")));
                cancel();
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    public void run() {
                        as.remove();
                        as3.remove();
                        as2.remove();
                    }
                }, 20 * 1);
                return;
            }
            //this.player.sendTitle(Format.chat("&eFarmen.."), Format.chat("&7" + this.seconds + "." + this.time + "&7s"), 0, 10, 0);\

            String holoLine2 = Drugs.instance.getConfig().getString("Hologram.HoloLine2")
                    .replace("%s%", "" + this.seconds)
                    .replace("%ms%", "" + this.time)
                    .replace("%p%", ""+this.player.getName());
            as2.setCustomName(Format.chat(holoLine2));

        }
            //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Format.chat("Aan het pakken: &e")+this.seconds+Format.chat("&e.")+this.time+Format.chat("&as")));
        if (this.seconds == 0 &&
                this.time == 0) {
            //this.player.sendTitle("", Format.chat("&a+1 Paddo"), 0, 60, 15);
            Drugs.farmers.remove(player.getUniqueId());
            Drugs.stands.remove(as.getUniqueId());
            Drugs.stands.remove(as2.getUniqueId());
            Drugs.stands.remove(as3.getUniqueId());
            as.remove();
            as2.setCustomName(Format.chat(Drugs.instance.getConfig().getString("Hologram.Klaar")));
            as3.remove();
            Drugs.blocks.remove(block.getLocation());
            player.getInventory().addItem(item);
            block.setType(Material.AIR);
            cancel();
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                public void run() {
                    as2.remove();
                }
            }, 20 * 1);
        }
    }
}