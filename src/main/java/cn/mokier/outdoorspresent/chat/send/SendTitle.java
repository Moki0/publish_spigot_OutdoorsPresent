package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendTitle extends BaseSend {

    public SendTitle(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                String title = vars.get(0);
                String subTitle = vars.size() > 1 ? vars.get(1) : "";
                Integer fadeIn = vars.size() > 2 ? Integer.parseInt(vars.get(2)) : 20;
                Integer stay = vars.size() > 3 ? Integer.parseInt(vars.get(3)) : 30;
                Integer fadeOut = vars.size() > 4 ? Integer.parseInt(vars.get(4)) : 20;

                player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
            } catch (Exception e) {
                throw new Exception("格式错误，正确格式  title: 主信息,副信息,淡入tick,持续tick,淡出tick ");
            }
        }
    }
}
