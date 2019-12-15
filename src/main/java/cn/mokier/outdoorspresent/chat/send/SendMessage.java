package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.command.CommandSender;

public class SendMessage extends BaseSend {

    public SendMessage(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        try {
            sender.sendMessage(vars.get(0));
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  messages: 信息 ");
        }
    }

}
