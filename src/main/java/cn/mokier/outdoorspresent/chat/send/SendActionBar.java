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

            try {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(vars.get(0)));
            } catch (Exception e) {
                throw new Exception("格式错误，正确格式  actionbar: 信息 ");
            }
        }
    }

}
