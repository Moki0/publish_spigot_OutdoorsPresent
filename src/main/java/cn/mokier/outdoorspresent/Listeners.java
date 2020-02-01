package cn.mokier.outdoorspresent;

import cn.mokier.outdoorspresent.present.Present;
import cn.mokier.outdoorspresent.present.PresentOper;
import io.izzel.taboolib.module.inject.TListener;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;


@TListener
public class Listeners implements Listener {

    @EventHandler
    public void onPresentDeath(EntityDeathEvent event) {
        Present present = PresentOper.getPresent(event.getEntity());

        if(present == null) {
            return;
        }

        present.onEntityDeathEvent(event);
    }

    @EventHandler
    public void onPresentDamage(EntityDamageByEntityEvent event) {
        Present present = PresentOper.getPresent(event.getEntity());

        if(present == null) {
            return;
        }

        present.onEntityDamageEvent(event);
    }

    /**
     * 区块加载时生成礼包
     * @param event
     */
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        for(int x=0; x<16; x++) {
            for(int z=0; z<16; z++) {
                for(int y=60; y<100; y++) {
                    Block block = chunk.getBlock(x, y, z);

                    if(block.getType() != Material.GRASS_BLOCK && block.getType() != Material.SAND) {
                        continue;
                    }
                    block = block.getLocation().add(0, 1, 0).getBlock();
                    Material type = block.getType();
                    Location location = block.getLocation();

                    if(type != Material.AIR && type != Material.GRASS) {
                        continue;
                    }

                    // 判断是否有实体
                    if(location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5).size() > 0) {
                        return;
                    }

                    PresentOper.create(location);
                    return;
                }
            }
        }

    }

    /**
     * 区块卸载时输出区块内的礼包
     * @param event
     */
    @EventHandler
    public void onChunkUnLoad(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();

        PresentOper.removeAll(chunk);
    }

    @EventHandler
    public void onBlockPhysics(BlockFromToEvent event) {
        Present present = PresentOper.getPresent(event.getToBlock().getLocation());

        if(present != null) {
            event.setCancelled(true);
        }
    }

    /**
     * 礼包禁止破坏
     * @param event
     */
    @EventHandler
    public void onBlockD(BlockBreakEvent event) {
        Present present = PresentOper.getPresent(event.getBlock().getLocation());

        if(present != null) {
            // 删除礼包
            present.remove();
            event.setCancelled(true);
        }
    }

    /**
     * 预防倒水刷头颅方块
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockBuild(PlayerBucketEmptyEvent event) {
        Location location = event.getBlockClicked().getLocation();
        for(Entity entity : location.getWorld().getNearbyEntities(location, 1.1, 1.1, 1.1)) {
            if(entity instanceof Shulker) {
                Present present = PresentOper.getPresent(entity);

                if(present != null) {
                    PresentOper.remove(present);
                }
            }
        }
    }

    /**
     * 预防出现bug的时候礼包实体和方块没删除的保护
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamager(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if(entity instanceof Shulker) {
            Block block = entity.getLocation().getBlock();

            if(PresentOper.getPresent(block.getLocation()) != null) {
                return;
            }

            if(block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
                block.setType(Material.AIR);
            }
        }
    }

    /**
     * 实体防火
     * @param event
     */
    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        Present present = PresentOper.getPresent(event.getEntity());

        if(present != null) {
            event.setCancelled(true);
        }
    }

    /**
     * 禁止推动礼包位置
     * @param event
     */
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        for(Block block : event.getBlocks()) {
            Present present = PresentOper.getPresent(block.getLocation());

            if(present != null) {
                event.setCancelled(true);
                return;
            }
        }
    }

    /**
     * 禁止拉动礼包位置
     * @param event
     */
    @EventHandler
    public void onBlockPistonExtend(BlockPistonRetractEvent event) {
        for(Block block : event.getBlocks()) {
            Present present = PresentOper.getPresent(block.getLocation());

            if(present != null) {
                event.setCancelled(true);
                return;
            }
        }
    }

}
