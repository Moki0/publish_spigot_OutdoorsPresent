package cn.mokier.outdoorspresent.chat.send;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendActionBarAll extends BaseSend {

    public SendActionBarAll(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        try {
            // 添加效果
            String msg = "§d§k§l| §r" + vars.get(0) + " §d§k§l|";

            for(Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
            }
        } catch (Exception e) {
            throw new Exception("格式错误，正确格式  actionbarAll: 信息 ");
        }
    }
}
