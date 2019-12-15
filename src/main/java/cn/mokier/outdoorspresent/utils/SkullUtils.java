package cn.mokier.outdoorspresent.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.TileEntitySkull;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

public class SkullUtils {

    public static ItemStack getSkullByID(String id) {
        ItemStack skull= new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwner(id);
        skull.setItemMeta(skullMeta);

        return skull;
    }

    /**
     * 通过url获得头颅
     * @param url http://textures.minecraft.net/texture/上的url
     * @return
     */
    public static ItemStack getSkullByURL(String url) {
        url = "http://textures.minecraft.net/texture/"+url;

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        ItemMeta skullMeta = skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    /**
     * 设置方块成头颅皮肤
     * @param block 方块
     * @param url http://textures.minecraft.net/texture/上的url
     */
    public static void setSkin(Block block, String url){
        url = "http://textures.minecraft.net/texture/"+url;

        if(block.getType() != Material.PLAYER_HEAD) {
            block.setType(Material.PLAYER_HEAD);
        }

        Skull skull = (Skull) block.getState();
        TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld)skull.getWorld()).getHandle().getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        skullTile.setGameProfile(profile);
    }

    /**
     * 判断方块头颅是否是制定皮肤
     * @param block 方块
     * @param url http://textures.minecraft.net/texture/上的url
     * @return
     */
    public static boolean isSkullCorrect(Block block, String url){
        Skull skull = (Skull) block.getState();
        TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld)skull.getWorld()).getHandle().getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
        GameProfile profile = skullTile.gameProfile;
        Collection<Property> textures = profile.getProperties().get("textures");
        String text = "";

        for (Property texture : textures) {
            text = texture.getValue();
        }

        skullTile.setGameProfile(profile);


        String decoded = Base64Coder.decodeString(text);
        String textureNumber = decoded.replace("{textures:{SKIN:{url:\"", "").replace("\"}}}", "").replace("http://textures.minecraft.net/texture/", "").trim();

        if(url.equalsIgnoreCase(textureNumber)){
            return true;
        }
        return false;
    }

}
