package cn.mokier.outdoorspresent;

import cn.mokier.outdoorspresent.chat.Chat;
import cn.mokier.outdoorspresent.chat.Lang;
import cn.mokier.outdoorspresent.present.PresentOper;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.config.TConfigWatcher;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.inject.TListener;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

@TListener
public final class OutdoorsPresent extends Plugin implements Listener {

    @Getter
    @TInject("settings.yml")
    private static TConfig settings;
    @Getter
    @TInject("lang.yml")
    private static TConfig lang;

    @Override
    public void onStarting() {
        PresentOper.load(settings);
        Lang.load(lang);

        TConfigWatcher.getInst().addSimpleListener(lang.getFile(), () -> Lang.load(lang));
    }

    @Override
    public void onStopping() {
        PresentOper.removeAll();

        TConfigWatcher.getInst().removeListener(lang.getFile());
    }

    public static void onReload(CommandSender sender) {
        settings.reload();
        lang.reload();

        Lang.load(lang);
        PresentOper.reload(settings);

        Chat.sendLang(sender, "reload");
    }

}
