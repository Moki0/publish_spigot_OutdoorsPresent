package cn.mokier.outdoorspresent.chat;

import cn.mokier.outdoorspresent.OutdoorsPresent;
import cn.mokier.outdoorspresent.chat.send.BaseSend;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class Send {

    private CommandSender sender;
    private List<String> lines;

    public Send(CommandSender sender, String line) {
        this(sender, Arrays.asList(line));
    }

    public Send(CommandSender sender, List<String> lines) {
        this.sender = sender;
        this.lines = lines;

        onExecuteon();
    }

    private void onExecuteon() {
        Bukkit.getScheduler().runTaskAsynchronously(OutdoorsPresent.getPlugin(), () -> {
            for(String line : lines) {
                BaseSend baseSend = SendType.readSend(line);

                try {
                    if(baseSend.getDealy() > 0) {
                        Thread.sleep(baseSend.getDealy());
                    }

                    Bukkit.getScheduler().runTask(OutdoorsPresent.getPlugin(), () -> {
                        try {
                            baseSend.onExecute(sender);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
