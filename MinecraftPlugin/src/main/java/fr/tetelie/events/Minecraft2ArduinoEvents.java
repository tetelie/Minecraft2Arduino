package fr.tetelie.events;

import fr.tetelie.Minecraft2Arduino;
import fr.tetelie.utils.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;

public class Minecraft2ArduinoEvents implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        if(e.getItem() != null && e.getItem().equals(Minecraft2Arduino.getInstance().getTool())) {
            if (e.getClickedBlock() != null) {
                e.setCancelled(true);

                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                    String name = e.getClickedBlock().getBlockData().getMaterial().toString().toLowerCase();
                    Position position = new Position(e.getClickedBlock().getLocation());

                    String new_name = Minecraft2Arduino.getInstance().addBlock(position, name);

                    if(!new_name.isEmpty()) {
                        Minecraft2Arduino.getInstance().addBlock(position, name);
                        player.sendMessage("§m§7--------------------------");
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "You registered a new block.");
                        player.sendMessage("§eclicked block: §7" + e.getClickedBlock().getBlockData().getMaterial());
                        player.sendMessage("§eposition: §7" + position);
                        player.sendMessage("§ename: §7" + new_name);
                        player.sendMessage("§7(§eHint§7) §euse '/m2a rename' to change name");
                        player.sendMessage("§m§7--------------------------");
                    }else{
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "Block already added!");
                    }
                }else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK))
                {
                    Position pos = new Position(e.getClickedBlock().getLocation());
                    if(Minecraft2Arduino.getInstance().getBlock().containsKey(pos)) {
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "You successfully removed " + Minecraft2Arduino.getInstance().getBlock().get(pos));
                        Minecraft2Arduino.getInstance().removeBlock(pos);
                    }
                }else{
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix + "Please interact with a block");
                }
            }
        }else if(e.getClickedBlock() != null)
        {
            Position pos = new Position(e.getClickedBlock().getLocation());
            if(Minecraft2Arduino.getInstance().getBlock().containsKey(pos))
            {
                Minecraft2Arduino.getInstance().sendMessage(Minecraft2Arduino.getInstance().getBlock().get(pos));

                if(Minecraft2Arduino.getInstance().getDebug().contains(player.getUniqueId()))
                {
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix+"§7[Debug] §e" + Minecraft2Arduino.getInstance().getBlock().get(pos) + " interacted.");
                }



            }

        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals(Minecraft2Arduino.getInstance().prefix+"GUI")){
            e.setCancelled(true);
            String name = e.getCurrentItem().getItemMeta().getItemName().replaceAll("§9", "");

            for(Map.Entry<Position, String> entry : Minecraft2Arduino.getInstance().getBlock().entrySet())
            {
                if(entry.getValue().equals(name))
                {
                    player.teleport(new Location(Bukkit.getWorld(entry.getKey().getWorld()), entry.getKey().getX(), entry.getKey().getY()+2, entry.getKey().getZ(), player.getLocation().getPitch(), player.getLocation().getYaw()));
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix+"You have been teleported to §9" + name);
                    return;
                }
            }


        }

    }

}
