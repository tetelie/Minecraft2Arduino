package fr.tetelie;

import fr.tetelie.commands.Minecraft2ArduinoCommand;
import fr.tetelie.events.Minecraft2ArduinoEvents;
import fr.tetelie.utils.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Minecraft2Arduino extends JavaPlugin {


    static Minecraft2Arduino instance;

    private ItemStack tool;

    public String prefix = "§7[§3M§f2§3A§7]: §e";

    private List<UUID> debug = new ArrayList<>();
    private HashMap<Position, String> block = new HashMap<>();

    public File saveFile;
    public YamlConfiguration saveConfig;

    @Override
    public void onEnable() {
        instance = this;
        registerResources();
        registerFiles();
        load();
        reigsterToolItem();
        registerCommands();
        registerEvents();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onDisable();
    }

    public void save() throws IOException {
        for (Map.Entry<Position, String> entry : Minecraft2Arduino.getInstance().getBlock().entrySet()) {
           saveConfig.set("save."+entry.getValue()+".position.world", entry.getKey().getWorld());
           saveConfig.set("save."+entry.getValue()+".position.x", entry.getKey().getX());
           saveConfig.set("save."+entry.getValue()+".position.y", entry.getKey().getY());
           saveConfig.set("save."+entry.getValue()+".position.z", entry.getKey().getZ());
        }
        System.out.println(prefix+"Successfully save: save.yml");

        saveConfig.save(saveFile);
    }

    private void load()
    {
        final ConfigurationSection cs = saveConfig.getConfigurationSection("save");
        if (cs != null) {
            for (final String s : cs.getKeys(false)) {
                if (s != null) {
                    final ConfigurationSection cs2 = cs.getConfigurationSection(s);
                    if (cs2 == null) {
                        continue;
                    }

                    System.out.println(cs2);
                    System.out.println(cs2.getName());

                    String world = saveConfig.getString("save."+cs2.getName()+".position.world");
                    int x = saveConfig.getInt("save."+cs2.getName()+".position.x");
                    int y = saveConfig.getInt("save."+cs2.getName()+".position.y");
                    int z = saveConfig.getInt("save."+cs2.getName()+".position.z");


                    Position pos = new Position(new Location(Bukkit.getWorld(world), x,y,z));
                    addBlock(pos, cs2.getName());


                    this.getServer().getConsoleSender().sendMessage(prefix + cs2.getName()+" has been loaded!");
                }
            }
        }
    }

    public static Minecraft2Arduino getInstance() {
        return instance;
    }

    private void registerResources() {
        saveResource("save.yml", false);
    }

    private void registerFiles() {
        saveFile = new File(getDataFolder() + "/save.yml");
        saveConfig = YamlConfiguration.loadConfiguration(saveFile);
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
                    saveConfig.set("save."+name, null);
                    return true;
                }
            }
        }
        return false;
    }

    public void removeBlock(Position position)
    {
        if(block.containsKey(position))
        {
            saveConfig.set("save."+block.get(position), null);
            block.remove(position);
        }
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
