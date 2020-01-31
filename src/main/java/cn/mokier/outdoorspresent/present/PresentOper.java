package cn.mokier.outdoorspresent.present;

import cn.mokier.outdoorspresent.chat.Chat;
import cn.mokier.outdoorspresent.customentity.PresentLiving;
import cn.mokier.outdoorspresent.utils.MsgUtils;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class PresentOper {

    /**
     * 礼包设定
     */
    private static List<PresentSet> sets;
    /**
     * 生成的礼包
     */
    @Getter
    private static List<Present> presents;

    public static void load(ConfigurationSection section) {
        sets = new ArrayList<>();
        presents = new ArrayList<>();

        for(String key : section.getConfigurationSection("present").getKeys(false)) {
            PresentSet set = getPresentSet(section.getConfigurationSection("present." + key));
            sets.add(set);
        }
    }

    public static void reload(ConfigurationSection section) {
        // 删除所有礼包
        removeAll();

        sets = new ArrayList<>();
        for(String key : section.getConfigurationSection("present").getKeys(false)) {
            PresentSet set = getPresentSet(section.getConfigurationSection("present." + key));
            sets.add(set);
        }
    }

    /**
     * 通过位置获得礼包
     * @param location 位置
     * @return
     */
    public static Present getPresent(Location location) {
        for(Present present : presents) {
            if(present.getLocation().getBlock().getLocation().equals(location.getBlock().getLocation())) {
                return present;
            }
        }

        return null;
    }

    /**
     * 通过实体获得礼包
     * @param entity 实体
     * @return
     */
    public static Present getPresent(Entity entity) {
        CraftEntity craftEntity = (CraftEntity) entity;
        if(!(craftEntity.getHandle() instanceof PresentLiving)) {
            return null;
        }

        for(Present present : presents) {
            if(present.equalsEntity(craftEntity.getHandle())) {
                return present;
            }
        }

        return null;
    }

    /**
     * 在一个位置生成礼包
     * @param location 位置
     * @param name 礼包名称
     */
    public static void create(Location location, String name, CommandSender sender) {
        PresentSet set = getPresentSet(name);

        // 没有这个礼包
        if(set == null) {
            Chat.sendLang(sender, "present.create.noName");
            return;
        }

        //这个位置已经有礼包则不再生成
        if(getPresent(location) != null) {
            return;
        }

        // 生成礼包
        try {
            Present present = new Present(location, set);
            presents.add(present);
        } catch (Exception e) {
            Chat.sendLang(sender, "present.create.error");
            e.printStackTrace();
        }
    }

    /**
     * 在一个位置生成礼包
     * @param location 位置
     */
    public static void create(Location location) {
        PresentSet set = oddsSpawn(location.getWorld());

        // 概率
        if(set == null) {
            return;
        }

        // 生成礼包
        try {
            Present present = new Present(location, set);
            presents.add(present);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得所有设定名称
     * @return
     */
    public static List<String> getPresentSets() {
        List<String> list = new ArrayList<>();

        for(PresentSet set : sets) {
            list.add(set.getName());
        }

        return list;
    }

    /**
     * 删除所有礼包
     */
    public static void removeAll() {
        for(Present present : presents) {
            present.remove();
        }

        presents = new ArrayList<>();
    }
    /**
     * 删除一个区块的所有礼包
     */
    public static void removeAll(Chunk chunk) {
        List<Present> list = new ArrayList<>();
        for (Present present : presents) {
            if(present.getLocation().getChunk().equals(chunk)) {
                list.add(present);
            }
        }

        for(Present present : list) {
            present.remove();
            presents.remove(present);
        }
    }

    /**
     * 输出一个礼包
     * @param present 礼包
     */
    public static void remove(Present present) {
        presents.remove(present);
        present.remove();
    }

    /**
     * 计算概率获取礼包设定
     * @return
     */
    private static PresentSet oddsSpawn(World world) {
        for(PresentSet set : sets) {
            if(set.isSpawnWorld(world.getName()) && set.spawnOdds()) {
                return set;
            }
        }

        return null;
    }

    /**
     * 通过礼包名称获得礼包
     * @param name 礼包名称
     * @return
     */
    private static PresentSet getPresentSet(String name) {
        for(PresentSet set : sets) {
            if(set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }

        return null;
    }

    /**
     * 通过配置获得礼包
     * @param section 配置
     * @return
     */
    private static PresentSet getPresentSet(ConfigurationSection section) {
        String displayName = MsgUtils.filterVar(section.getString("displayName"));
        String skin = section.getString("skin");
        List<String> play = section.getStringList("play");
        Integer updateInterval = section.getInt("update.interval");
        List<String> updatePlay = section.getStringList("update.play");
        Double hologramAddY = section.getDouble("hologram.addY");
        Double linvingMaxHealth = section.getDouble("livning.maxHealth");
        Integer livningDamageItemDurability = section.getInt("livning.damage.itemDurability");
        List<String> livningDamagePlay = section.getStringList("livning.damage.play");
        List<String> spawnWorlds = section.getStringList("spawn.worlds");
        String spawnOdds = section.getString("spawn.odds");
        List<String> items = section.getStringList("items");

        PresentSet set = new PresentSet(section.getName(), displayName, skin, play, updateInterval, updatePlay, hologramAddY, linvingMaxHealth, livningDamageItemDurability, livningDamagePlay, spawnWorlds, spawnOdds, items);
        return set;
    }

}
