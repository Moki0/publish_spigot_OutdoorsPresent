package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCommand extends BaseSend {

    public SendCommand(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {

                player.performCommand(vars.get(0));
            } catch (Exception e) {
                throw new Exception("格式错误，正确格式  command: 指令 ");
            }
        }
    }
}
