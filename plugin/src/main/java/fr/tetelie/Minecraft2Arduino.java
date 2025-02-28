package fr.tetelie;

import fr.tetelie.commands.Minecraft2ArduinoCommand;
import fr.tetelie.events.Minecraft2ArduinoEvents;
import fr.tetelie.utils.Position;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Minecraft2Arduino extends JavaPlugin {


    static Minecraft2Arduino instance;

    private ItemStack tool;

    public String prefix = "§7[§eM2A§7]: §e";

    private List<UUID> debug = new ArrayList<>();
    private HashMap<Position, String> block = new HashMap<>();



    @Override
    public void onEnable() {
        instance = this;
        reigsterToolItem();
        registerCommands();
        registerEvents();
        super.onEnable();
    }

    public static Minecraft2Arduino getInstance() {
        return instance;
    }

    private void registerEvents()
    {
        getServer().getPluginManager().registerEvents(new Minecraft2ArduinoEvents(), this);
    }

    private void registerCommands()
    {
        this.getCommand("minecraft2arduino").setExecutor(new Minecraft2ArduinoCommand());
    }

    public void reigsterToolItem()
    {
        tool = new ItemStack(Material.STICK);
        ItemMeta meta = tool.getItemMeta();
        meta.setItemName("§eMinecraft2Arduino register tool §7(Right / Left)");
        tool.setItemMeta(meta);
    }

    public ItemStack getTool() {
        return tool;
    }

    public HashMap<Position, String> getBlock() {
        return block;
    }

    public String addBlock(Position position, String name)
    {
        if(block.containsKey(position))
        {
            return "";
        }else if(block.containsValue(name))
        {
            String base = name;
            while(block.containsValue(base))
            {
                base = incrementString(base);
            }
            name = base;
        }


        block.put(position, name);

        return name;
    }

    public boolean removeBlock(String name)
    {
        if(block.containsValue(name))
        {
            for(Position n : block.keySet())
            {
                if(block.get(n).equals(name))
                {
                    block.remove(n);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeBlock(Position position)
    {
        if(block.containsKey(position))
        {
            block.remove(position);
            return true;
        }
        return false;
    }


    private String incrementString(String str) {
        String base = str.replaceAll("\\d+$", ""); // Supprime les chiffres à la fin
        String number = str.replaceAll("^\\D+", ""); // Garde uniquement les chiffres

        int i = number.isEmpty() ? 0 : Integer.parseInt(number); // Convertit en int ou 0 si vide
        return base + (i + 1); // Incrémente et concatène
    }

    public List<UUID> getDebug() {
        return debug;
    }
}
