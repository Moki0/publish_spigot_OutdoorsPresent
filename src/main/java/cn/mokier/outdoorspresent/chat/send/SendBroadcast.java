package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SendBroadcast extends BaseSend {

    public SendBroadcast(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        try {
            Bukkit.broadcastMessage(vars.get(0));
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  broadcast: 信息 ");
        }
    }
}
