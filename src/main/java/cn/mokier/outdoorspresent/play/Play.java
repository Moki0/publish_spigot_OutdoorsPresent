package cn.mokier.outdoorspresent.play;

import cn.mokier.outdoorspresent.OutdoorsPresent;
import cn.mokier.outdoorspresent.play.effect.BasePlay;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

public class Play {

    private Location location;
    private List<String> lines;

    public Play(Location location, String line) {
        this(location, Arrays.asList(line));
    }

    public Play(Location location, List<String> lines) {
        this.location = location.clone();
        this.lines = lines;

        onExecuteon();
    }

    private void onExecuteon() {
        Bukkit.getScheduler().runTaskAsynchronously(OutdoorsPresent.getPlugin(), () -> {
            for(String line : lines) {
                try {
                    BasePlay baseSend = PlayType.readSend(line);

                    if(baseSend.getDealy() > 0) {
                        Thread.sleep(baseSend.getDealy());
                    }

                    Bukkit.getScheduler().runTask(OutdoorsPresent.getPlugin(), () -> {
                        try {
                            baseSend.onExecute(location);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
