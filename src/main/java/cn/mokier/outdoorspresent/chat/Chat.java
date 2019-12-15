package cn.mokier.outdoorspresent.chat;

import cn.mokier.outdoorspresent.utils.MsgUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Chat {

    public static void sendLang(CommandSender sender, String node, String... replaces) {
        if(sender == null || Lang.getMessage(node) == null) {
            return;
        }

        sendMsg(sender, Lang.getMessage(node), replaces);
    }

    public static void sendMsg(CommandSender sender, List<String> msgs, String... replaces) {
        if(sender == null) {
            return;
        }
        new Send(sender, MsgUtils.filterVar(msgs, replaces));
    }

    public static void sendMsg(CommandSender sender, String msg, String... replaces) {
        if(sender == null) {
            return;
        }
        new Send(sender, MsgUtils.filterVar(msg, replaces));
    }

}
