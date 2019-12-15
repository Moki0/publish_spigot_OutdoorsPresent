package cn.mokier.outdoorspresent.play.effect;

import org.bukkit.Location;

public class PlayDelay extends BasePlay {

    public PlayDelay(String line) {
        super(line);
    }

    @Override
    public void onExecute(Location location) throws Exception {
        try {
            dealy = Integer.parseInt(vars.get(0));
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  delay: 毫秒 ");
        }
    }
}
