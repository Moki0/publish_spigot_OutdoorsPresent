package cn.mokier.outdoorspresent.present;

import cn.mokier.outdoorspresent.utils.MsgUtils;
import cn.mokier.soulitem.api.SoulItemAPI;
import cn.mokier.soulitem.item.SItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class PresentSet {

    private String name;
    private String displayName;
    private String skin;
    private List<String> play;
    private Integer updateInterval;
    private List<String> updatePlay;
    private Double hologramAddY;
    private Double livningMaxHealth;
    private Integer livningDamageItemDurability;
    private List<String> livningDamagePlay;
    private List<String> spawnWorlds;
    private String spawnOdds;

    private List<String> items;

    /**
     * 判断是否是生成的世界
     * @param name
     * @return
     */
    public boolean isSpawnWorld(String name) {
        return spawnWorlds != null && spawnWorlds.contains(name);
    }

    /**
     * 计算生成概率
     * @return
     */
    public boolean spawnOdds() {
        return MsgUtils.odds(spawnOdds);
    }

    /**
     * 计算概率获得物品集
     * @return
     */
    public List<ItemStack> oddsItems() throws Exception {
        List<ItemStack> list = new ArrayList<>();

        try {
            for(String msg : items) {
                if(msg.startsWith("soulitem: ")) {
                    msg = msg.replace("soulitem: ", "");

                    String[] split = msg.split(",");
                    String id = split[0];
                    Integer amount = Integer.parseInt(split[1]);
                    String odds = split[2];

                    if(MsgUtils.odds(odds)) {
                        SItem sItem = SoulItemAPI.getSItem(id);
                        ItemStack itemStack = sItem.getItemStack();
                        itemStack.setAmount(amount);
                        list.add(itemStack);
                    }
                }else {
                    String[] split = msg.split(",");
                    String id = split[0];
                    Integer amount = Integer.parseInt(split[1]);
                    String odds = split[2];

                    if(MsgUtils.odds(odds)) {
                        ItemStack itemStack = new ItemStack(Material.matchMaterial(id), amount);
                        list.add(itemStack);
                    }
                }

            }
        } catch (Exception e) {
            throw new Exception("宝箱物品内容设置错误");
        }

        return list;
    }

    public boolean hasLivning() {
        return livningMaxHealth != null;
    }

    public boolean hasUpdate() {
        return updateInterval != null;
    }

}
