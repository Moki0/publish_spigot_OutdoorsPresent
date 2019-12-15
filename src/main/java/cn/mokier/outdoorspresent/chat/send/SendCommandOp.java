package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCommandOp extends BaseSend {

    public SendCommandOp(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                boolean isOp = player.isOp();
                player.setOp(true);
                player.performCommand(vars.get(0));
                player.setOp(isOp);
            } catch (Exception e) {
                throw new Exception("格式错误，正确格式  op: 指令 ");
            }
        }
    }
}
