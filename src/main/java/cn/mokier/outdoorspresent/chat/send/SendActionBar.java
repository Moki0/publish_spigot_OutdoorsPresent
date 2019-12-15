package cn.mokier.outdoorspresent.chat.send;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendActionBar extends BaseSend {

    public SendActionBar(String line) {
        super(line);
    }

    @Override
    public void onExecute(CommandSender sender) throws Exception {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // 添加效果
            String msg = "§d§k§l| §r" + vars.get(0) + " §d§k§l|";

            try {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
            } catch (Exception e) {
                throw new Exception("格式错误，正确格式  actionbar: 信息 ");
            }
        }
    }

}
