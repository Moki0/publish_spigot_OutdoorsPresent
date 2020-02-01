package cn.mokier.outdoorspresent.present;

import cn.mokier.outdoorspresent.OutdoorsPresent;
import cn.mokier.outdoorspresent.chat.Chat;
import cn.mokier.outdoorspresent.play.Play;
import cn.mokier.outdoorspresent.utils.SkullUtils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

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
    private Shulker living;

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
        living = set.hasLivning() ? spawnLiving(location) : null;

        // 创建更新线程
        task = set.hasUpdate() ? Bukkit.getScheduler().runTaskTimer(OutdoorsPresent.getPlugin(), () -> update(), set.getUpdateInterval(), set.getUpdateInterval()) : null;
    }

    public void update() {
        // 播放效果
        new Play(location, set.getUpdatePlays());

        // 实体消失删除礼包
        if(living == null || living.isDead()) {
            PresentOper.remove(this);
        }
    }

    /**
     * 打开礼包
     */
    public void open(Player player) {
        //删除宝箱
        remove();

        // 丢物品
        World world = location.getWorld();
        for(ItemStack itemStack : items) {
            world.dropItem(location, itemStack);
        }

        // 发送信息
        Chat.sendMsg(player, set.getSends(),
                "{player}", player.getName(),
                "{name}", set.getDisplayName());

        // 播放效果
        new Play(location, set.getPlays());

    }

    /**
     * 删除这个礼包
     */
    public void remove() {
        block.setType(Material.AIR);
        hologram.delete();

        if(living != null) {
            living.remove();
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

    /**
     * 实体被攻击
     */
    public void onEntityDeathEvent(EntityDeathEvent event) {
        Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        open(player);
    }

    /**
     * 实体死亡
     */
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
        // 播放效果
        new Play(location, set.getLivningDamagePlays());

        // 武器耐久倍减
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            ItemMeta meta = itemStack.getItemMeta();

            if (meta != null) {
                Damageable damageable = (Damageable) meta;

                damageable.setDamage(damageable.getDamage() + set.getLivningDamageItemDurability());
                itemStack.setItemMeta(meta);
            }
        }
    }

    /**
     * 生成宝箱实体
     * @param location
     * @return
     */
    public Shulker spawnLiving(Location location) {
        Shulker living = (Shulker) location.getWorld().spawnEntity(location, EntityType.SHULKER);

        living.setAI(false);

        PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false);
        living.addPotionEffect(effect);

        Double health = set.getLivningMaxHealth();
        living.setMaxHealth(health);
        living.setHealth(health);

        living.setCustomName(" ");

        return living;
    }

}
