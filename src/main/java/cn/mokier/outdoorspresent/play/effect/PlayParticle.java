package cn.mokier.outdoorspresent.play.effect;

import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * 播放粒子
 */
public class PlayParticle extends BasePlay {

    public PlayParticle(String line) {
        super(line);
    }

    @Override
    public void onExecute(Location location) throws Exception {
        try {
            String type = vars.get(0).toUpperCase();
            Integer count = vars.size() > 1 ? Integer.parseInt(vars.get(1)) : 1;
            Double offsetX = vars.size() > 2 ? Double.parseDouble(vars.get(2)) : 0;
            Double offsetY = vars.size() > 3 ? Double.parseDouble(vars.get(3)) : 0.1;
            Double offsetZ = vars.size() > 4 ? Double.parseDouble(vars.get(4)) : 0;
            Double extra = vars.size() > 5 ? Double.parseDouble(vars.get(5)) : 0;

            location.getWorld().spawnParticle(Particle.valueOf(type), location, count, offsetX, offsetY, offsetZ, extra, null);
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  particle: 类型,数量,偏移X,偏移Y,偏移Z,增大 ");
        }
    }
}
