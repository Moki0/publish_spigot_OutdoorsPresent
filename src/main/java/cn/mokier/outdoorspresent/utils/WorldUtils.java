package cn.mokier.outdoorspresent.utils;

import cn.mokier.outdoorspresent.OutdoorsPresent;
import lombok.NonNull;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WorldUtils {

    public static List<Block> getNearbyBlocks(Location location, int radius, Material type) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if(type==null || block.getType() == type) {
                        blocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * 判断一个位置是否在另一个位置的制定范围
     * @param location 主位置
     * @param to 次位置
     * @param range 范围
     * @return
     */
    public static boolean isLocationRange(Location location, Location to, Integer range, boolean isHigh) {
        double x = Math.abs(location.getX()-to.getX());
        double y = Math.abs(location.getY()-to.getY());
        double z = Math.abs(location.getZ()-to.getZ());

        ++range;
        if(isHigh) {
            return x < range && y < range && z < range;
        }else {
            return x < range && z < range;
        }
    }

    public static Location varyLocation(String str) {
        World world = Bukkit.getWorld(str.split(",")[0]);
        double x = Double.parseDouble(str.split(",")[1]);
        double y = Double.parseDouble(str.split(",")[2]);
        double z = Double.parseDouble(str.split(",")[3]);
        float yaw = Float.parseFloat(str.split(",")[4]);
        float pitch = Float.parseFloat(str.split(",")[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String varyLocation(Location location) {
        String world = location.getWorld().getName();
        DecimalFormat df = new DecimalFormat("0.00");
        String x = df.format(location.getX());
        String y = df.format(location.getY());
        String z = df.format(location.getZ());
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return world+","+x+","+y+","+z+","+yaw+","+pitch;
    }

    /**
     * 获得一个位置的上面第一个空白位置
     * @param location 位置
     * @return null 64格内没有空白位置
     */
    public static Location getLocationTop(Location location) {
        for(int i=0; i<64; i++) {
            Block block = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY()+i, location.getBlockZ());
            Block blockA = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY()+i+1, location.getBlockZ());

            if(block.getType().equals(Material.AIR) && blockA.getType().equals(Material.AIR)) {
                return block.getLocation();
            }
        }

        return null;
    }

}
