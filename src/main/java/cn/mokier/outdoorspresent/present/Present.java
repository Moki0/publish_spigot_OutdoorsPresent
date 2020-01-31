package cn.mokier.outdoorspresent.present;

import cn.mokier.outdoorspresent.OutdoorsPresent;
import cn.mokier.outdoorspresent.customentity.PresentLiving;
import cn.mokier.outdoorspresent.play.Play;
import cn.mokier.outdoorspresent.utils.SkullUtils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.server.v1_14_R1.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

@ToString
public class Present {

    @Getter
    private Block block;
    @Getter
    private PresentSet set;
    @Getter
    private Location location;
    private Hologram hologram;
    private List<ItemStack> items;

    @Getter
    private PresentLiving living;

    private BukkitTask task;

    public Present(Location location, PresentSet set) throws Exception {
        this.block = location.getBlock();
        this.set = set;
        this.location = location = location.getBlock().getLocation().add(0.5, 0, 0.5);

        // 创建方块
        SkullUtils.setSkin(block, set.getSkin());

        // 创建全息
        hologram = HologramsAPI.createHologram(OutdoorsPresent.getPlugin(), location.clone().add(0, set.getHologramAddY(), 0));
        hologram.appendTextLine(getDisplayName());

        // 获取概率物品
        items = set.oddsItems();

        // 创建实体
        living = set.hasLivning() ? PresentLiving.spawnEntity(this, location) : null;

        // 创建更新线程
        task = set.hasUpdate() ? Bukkit.getScheduler().runTaskTimer(OutdoorsPresent.getPlugin(), () -> update(), set.getUpdateInterval(), set.getUpdateInterval()) : null;
    }

    public void update() {
        // 播放效果
        new Play(location, set.getUpdatePlay());

        // 实体消失删除礼包
        if(living == null || living.dead) {
            PresentOper.remove(this);
        }
    }

    /**
     * 打开礼包
     */
    public void open() {
        //删除宝箱
        remove();

        // 播放效果
        new Play(location, set.getPlay());

        // 丢物品
        World world = location.getWorld();
        for(ItemStack itemStack : items) {
            world.dropItem(location, itemStack);
        }
    }

    /**
     * 删除这个礼包
     */
    public void remove() {
        block.setType(Material.AIR);
        hologram.delete();

        if(living != null) {
            living.die();
        }

        if(task != null) {
            task.cancel();
        }
    }

    /**
     * 是否有生命
     * @return
     */
    public boolean hasLivning() {
        return set.hasLivning();
    }

    /**
     * 获得宝箱显示名
     * @return
     */
    public String getDisplayName() {
        return set.getDisplayName();
    }

    /**
     * 判断一个实体是否是这个礼包的实体
     * @param entity 实体
     * @return
     */
    public boolean equalsEntity(Entity entity) {
        if(!hasLivning()) {
            return false;
        }
        return living.equals(entity);
    }

}
