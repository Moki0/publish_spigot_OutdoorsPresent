package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.command.CommandSender;

public class SendDelay extends BaseSend {

    public SendDelay(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        try {
            dealy = Integer.parseInt(vars.get(0));
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  delay: 毫秒 ");
        }
    }
}
