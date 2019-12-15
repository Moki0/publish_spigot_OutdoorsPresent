package cn.mokier.outdoorspresent.play.effect;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class PlayFirework extends BasePlay {

    public PlayFirework(String line) {
        super(line);
    }

    @Override
    public void onExecute(Location location) throws Exception {
        try {
            Integer power = Integer.parseInt(vars.get(0));
            String type = vars.size() > 1 ? vars.get(1).toUpperCase() : "BALL_LARGE";
            Integer red = vars.size() > 2 ? Integer.parseInt(vars.get(2)) : 196;
            Integer green = vars.size() > 3 ? Integer.parseInt(vars.get(3)) : 81;
            Integer blue = vars.size() > 4 ? Integer.parseInt(vars.get(4)) : 244;
            Boolean flicker = vars.size() > 5 ? Boolean.parseBoolean(vars.get(5)) : false;


            Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta mete = firework.getFireworkMeta();

            mete.setPower(power);
            mete.addEffect(FireworkEffect.builder().withColor(Color.fromBGR(blue, green, red)).with(FireworkEffect.Type.valueOf(type)).flicker(flicker).build());

            firework.setFireworkMeta(mete);

        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  firework: 力度,类型,RGB红值,RGB绿值,RGB蓝值,闪烁 ");
        }
    }
}
