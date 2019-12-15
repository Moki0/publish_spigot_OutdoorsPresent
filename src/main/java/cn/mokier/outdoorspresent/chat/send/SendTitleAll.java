package cn.mokier.outdoorspresent.chat.send;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendTitleAll extends BaseSend {

    public SendTitleAll(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        try {
            String title = vars.get(0);
            String subTitle = vars.size() > 1 ? vars.get(1) : "";
            Integer fadeIn = vars.size() > 2 ? Integer.parseInt(vars.get(2)) : 20;
            Integer stay = vars.size() > 3 ? Integer.parseInt(vars.get(3)) : 30;
            Integer fadeOut = vars.size() > 4 ? Integer.parseInt(vars.get(4)) : 20;

            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
            }
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  titleAll: 主信息,副信息,淡入tick,持续tick,淡出tick ");
        }
    }
}