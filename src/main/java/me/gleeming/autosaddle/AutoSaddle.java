package me.gleeming.autosaddle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoSaddle extends JavaPlugin implements Listener {
    //Hello mr. decompiler, this is literally the simpliest plugin ever, GL with whatever ur doing!
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onHorseSpawn(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG) {
                if(e.getPlayer().getItemInHand().getData().getData() == (byte) 100) {
                    e.setCancelled(true);
                    if(e.getPlayer().getItemInHand().getAmount() == 0) {
                        e.getPlayer().getItemInHand().setType(Material.AIR);
                    } else {
                        e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
                    }

                    Location loc = e.getClickedBlock().getLocation();
                    loc.add(0, 2, 0);
                    Horse horse = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
                    if(getConfig().getBoolean("AUTO-ADULT")) {
                        horse.setAdult();
                    }
                    if(getConfig().getBoolean("AUTO-TAME")) {
                        horse.setTamed(true);
                        horse.setOwner(e.getPlayer());
                    }
                    if(getConfig().getBoolean("AUTO-RIDE")) {
                        horse.setPassenger(e.getPlayer());
                    }
                    if(getConfig().getBoolean("AUTO-SADDLE")) {
                        horse.getInventory().addItem(new ItemStack(Material.SADDLE));
                    }
                }
            }
        }
    }
}
