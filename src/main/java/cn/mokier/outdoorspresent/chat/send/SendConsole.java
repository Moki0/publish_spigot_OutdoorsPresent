package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SendConsole extends BaseSend {

    public SendConsole(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), vars.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("格式错误，正确格式  console: 指令 ");
        }
    }
}
