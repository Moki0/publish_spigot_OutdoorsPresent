package cn.mokier.outdoorspresent;

import cn.mokier.outdoorspresent.present.Present;
import cn.mokier.outdoorspresent.present.PresentOper;
import io.izzel.taboolib.module.inject.TListener;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Iterator;

@TListener
public class Listeners implements Listener {

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

                    if(block.getType() != Material.GRASS_BLOCK) {
                        continue;
                    }
                    block = block.getLocation().add(0, 1, 0).getBlock();
                    Material type = block.getType();

                    if(type != Material.AIR && type != Material.GRASS) {
                        continue;
                    }

                    PresentOper.create(block.getLocation());
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


        Iterator<Present> iterator = PresentOper.getPresents().iterator();
        while (iterator.hasNext()) {
            Present present = iterator.next();

            if(present.getLocation().getChunk().equals(chunk)) {
                iterator.remove();
                present.remove();
            }
        }
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
            event.setCancelled(true);
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

    // 删除非法物品

}
