package cn.mokier.outdoorspresent.play.effect;

import org.bukkit.Location;

/**
 * 位置偏移
 */
public class PlayLocation extends BasePlay {

    public PlayLocation(String line) {
        super(line);
    }

    @Override
    public void onExecute(Location location) throws Exception {
        try {
            Double x = Double.parseDouble(vars.get(0));
            Double y = Double.parseDouble(vars.get(1));
            Double z = Double.parseDouble(vars.get(2));

            location.add(x, y, z);
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  location: X偏移值,Y偏移值,Z偏移值 ");
        }
    }

}
