package fr.tetelie.utils;

import org.bukkit.Location;

import java.util.Objects;

public class Position {

    private String world;
    private int x;
    private int y;
    private int z;

    public Position(Location location)
    {
        world = location.getWorld().getName();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    @Override
    public String toString() {
        return "world("+world+"),x("+x+"),y("+y+"),z("+z+")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position pos)) return false;
        return world.equals(pos.getWorld()) && x == pos.getX() && y == pos.getY() && z == pos.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(world,x,y,z);
    }
}
