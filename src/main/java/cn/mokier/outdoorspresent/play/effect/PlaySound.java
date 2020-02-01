package cn.mokier.outdoorspresent.play.effect;

import org.bukkit.Location;
import org.bukkit.Sound;

/**
 * 播放环境声音
 */
public class PlaySound extends BasePlay {

    public PlaySound(String line) {
        super(line);
    }

    @Override
    public void onExecute(Location location) throws Exception {
        try {
            String type = vars.get(0).toUpperCase();
            Integer volume = vars.size() > 1 ? Integer.parseInt(vars.get(1)) : 1;
            Integer pitch = vars.size() > 2 ? Integer.parseInt(vars.get(2)) : 0;

            location.getWorld().playSound(location, Sound.valueOf(type), volume, pitch);
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  sound: 类型,音量,音高 ");
        }
    }
}
