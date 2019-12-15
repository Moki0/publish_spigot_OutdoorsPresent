package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendSound extends BaseSend {

    public SendSound(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                String type = vars.get(0).toUpperCase();
                Integer volume = vars.size() > 1 ? Integer.parseInt(vars.get(1)) : 1;
                Integer pitch = vars.size() > 2 ? Integer.parseInt(vars.get(2)) : 0;

                player.playSound(player.getLocation(), type, volume, pitch);
            } catch (Exception e) {
                throw new Exception("格式错误，正确格式  sound: 类型,音量,音高 ");
            }
        }
    }
}
