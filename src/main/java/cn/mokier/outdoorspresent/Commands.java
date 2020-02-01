package cn.mokier.outdoorspresent;

import cn.mokier.outdoorspresent.present.PresentOper;
import io.izzel.taboolib.module.command.base.*;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand(name = "outdoorspresent", aliases = {"present"})
public class Commands extends BaseMainCommand {

    @SubCommand(description = "在脚下位置生成一个礼包", permission = "outdoorspresent.create", type = CommandType.PLAYER)
    BaseSubCommand create = new BaseSubCommand() {

        @Override
        public Argument[] getArguments() {
            return new Argument[] { new Argument("礼包", true, () -> PresentOper.getPresentSets()) };
        }

        @Override
        public void onCommand(CommandSender sender, Command command, String label, String[] args) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            String name = args[0];

            PresentOper.create(location, name, sender);
        }

    };

    @SubCommand(description = "重新加载配置文件", permission = "outdoorspresent.reload")
    BaseSubCommand reload = new BaseSubCommand() {

        @Override
        public void onCommand(CommandSender sender, Command command, String label, String[] args) {
            OutdoorsPresent.onReload(sender);
        }

    };

}
