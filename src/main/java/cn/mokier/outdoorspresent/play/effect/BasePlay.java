package cn.mokier.outdoorspresent.play.effect;

import cn.mokier.outdoorspresent.play.PlayType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePlay {


    protected PlayType type;
    protected List<String> vars;
    protected Integer dealy;

    public BasePlay(String line) {
        dealy = 0;
        type = PlayType.get(line);
        line = line.replace(type.getName()+": ", "");

        vars = new ArrayList<>();
        for(String var : line.split(",")) {
            vars.add(var);
        }
    }

    /**
     * 执行播放方法
     * @param location 位置
     * @throws Exception 执行异常
     */
    public abstract void onExecute(Location location) throws Exception;

    public Integer getDealy() throws Exception {
        if(type == PlayType.DELAY) {
            onExecute(null);
        }
        return dealy;
    }
}
