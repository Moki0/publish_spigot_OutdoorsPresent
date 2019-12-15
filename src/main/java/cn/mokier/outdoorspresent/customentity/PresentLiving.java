package cn.mokier.outdoorspresent.customentity;

import cn.mokier.outdoorspresent.play.Play;
import cn.mokier.outdoorspresent.present.Present;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class PresentLiving extends EntityShulker {

    private Present present;
    public boolean extended = true;

    public PresentLiving(EntityTypes<? extends EntityShulker> entitytypes, World world) {
        super(entitytypes, world);

        this.setNoAI(true);
        this.setCustomName(CraftChatMessage.fromStringOrNull("§r"));

        MobEffect effect = new MobEffect(MobEffectList.fromId(CraftPotionEffectType.INVISIBILITY.getId()), 999999, 1, false, false);
        this.addEffect(effect);
    }

    @SuppressWarnings("rawtypes")
    public static PresentLiving spawnEntity(Present present, Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        Entity nmsEntity = EntityLoader.PRESENT.spawnCreature(world, null, null, position, EnumMobSpawn.EVENT, true, false);

        // 设置礼包数据
        PresentLiving entity = (PresentLiving) nmsEntity.getBukkitEntity().getHandle();
        entity.present = present;

        Double health = present.getSet().getLivningMaxHealth();
        entity.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(health);
        entity.setHealth(health.floatValue());

        return entity;
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        boolean b = super.damageEntity(damagesource, f);

        if(present == null) {
            return b;
        }

        if(!isAlive()) {
            present.open();
        }else {
            new Play(present.getLocation(), present.getSet().getLivningDamagePlay());
        }

        // 武器耐久倍减
        if(damagesource.j() instanceof EntityPlayer) {
            Player player = Bukkit.getPlayer(damagesource.j().getUniqueID());
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            ItemMeta meta = itemStack.getItemMeta();

            if (meta != null) {
                Damageable damageable = (Damageable) meta;

                damageable.setDamage(damageable.getDamage() - present.getSet().getLivningDamageItemDurability());
                itemStack.setItemMeta(meta);
            }
        }

        return b;
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("extended", extended);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        extended = nbttagcompound.getBoolean("extended");
    }

}
