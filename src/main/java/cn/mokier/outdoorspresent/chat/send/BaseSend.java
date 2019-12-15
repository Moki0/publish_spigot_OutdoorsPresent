package cn.mokier.outdoorspresent.chat.send;

import cn.mokier.outdoorspresent.chat.SendType;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSend {

    protected SendType type;
    protected List<String> vars;
    protected Integer dealy;

    public BaseSend(String line) {
        dealy = 0;
        type = SendType.get(line);
        line = line.replace(type.getName()+": ", "");

        vars = new ArrayList<>();
        for(String var : line.split(",")) {
            vars.add(var);
        }
    }

    /**
     * 执行消息方法
     * @param player 玩家
     * @throws Exception 执行异常
     */
    public abstract void onExecute(CommandSender player) throws Exception;

    public Integer getDealy() throws Exception {
        if(type == SendType.DELAY) {
            onExecute(null);
        }
        return dealy;
    }

}
