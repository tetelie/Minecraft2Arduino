package fr.tetelie.commands;

import fr.tetelie.Minecraft2Arduino;
import fr.tetelie.utils.Position;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Minecraft2ArduinoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player)
        {
            Player player = ((Player) commandSender).getPlayer();
            if(args.length == 0)
            {
                player.getInventory().addItem(Minecraft2Arduino.getInstance().getTool());
                player.sendMessage(Minecraft2Arduino.getInstance().prefix + "You recieved Minecraft2Arduino item!");
                player.sendMessage("§7   - Right click to register");
                player.sendMessage("§7   - Left click to remvoe");
                return true;
            }else if(args.length == 1)
            {
                if(args[0].equals("help"))
                {
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix + "/m2a §7#Give you register item");
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix + "/m2a debug §7#Toggle debug mode");
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix + "/m2a remove <name> §7# Remove block from name");
                    player.sendMessage(Minecraft2Arduino.getInstance().prefix + "/m2a rename <old_name> <new_name> §7# Rename block name");
                    return true;
                }else if(args[0].equals("debug"))
                {
                    if(Minecraft2Arduino.getInstance().getDebug().contains(player.getUniqueId()))
                    {
                        Minecraft2Arduino.getInstance().getDebug().remove(player.getUniqueId());
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "You exited debug mode.");
                    }else{
                        Minecraft2Arduino.getInstance().getDebug().add(player.getUniqueId());
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "You entered debug mode.");
                    }
                    return true;
                }
            } else if(args.length == 2)
            {
                if(args[0].equals("remove"))
                {
                    boolean test = Minecraft2Arduino.getInstance().removeBlock(args[1]);
                    if(test)
                    {
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "Sucessfully removed " + args[1]);
                    }else {
                        player.sendMessage(Minecraft2Arduino.getInstance().prefix + "Couldn't find " + args[1]);
                    }
                    return true;
                }
            }else if(args.length == 3) {
                if (args[0].equals("rename")) {
                    if (Minecraft2Arduino.getInstance().getBlock().containsValue(args[1])) {
                        if (!Minecraft2Arduino.getInstance().getBlock().containsValue(args[2])) {

                            for (Map.Entry<Position, String> entry : Minecraft2Arduino.getInstance().getBlock().entrySet()) {
                                {
                                    if (entry.getValue().equals(args[1])) {
                                        Minecraft2Arduino.getInstance().getBlock().put(entry.getKey(), args[2]);
                                        player.sendMessage(Minecraft2Arduino.getInstance().prefix+"Successfully renamed "+ args[1] + " to " + args[2] + "!");
                                        return true;
                                    }
                                }
                            }
                            }else{
                                player.sendMessage(Minecraft2Arduino.getInstance().prefix + "name '" + args[2] + "' already taken!");

                        }

                        } else {
                            player.sendMessage(Minecraft2Arduino.getInstance().prefix + "Couldn't find " + args[1]);
                        }
                        return true;

                }
            }


        }else {
            System.out.println("Please perform this command as a player.");
        }

        return false;
    }
}
