package cn.mokier.outdoorspresent.chat;

import cn.mokier.outdoorspresent.chat.send.*;

public enum SendType {

    /**
     * 发送一条 Actionbar 消息
     */
    ACTIONBAR("actionbar"),

    /**
     * 向全体在线玩家发送一条 Actionbar 消息
     */
    ACTIONBAR_ALL("actionbarall"),

    /**
     * 向全体在线玩家发送一条消息
     */
    BROADCAST("broadcast"),

    /**
     * 通过后台执行某命令
     */
    CONSOLE("console"),

    /**
     * 延时多久再继续执行命令组
     */
    DELAY("delay"),

    /**
     * 发送一条 聊天框 消息
     */
    MESSAGE("message"),

    /**
     * 以玩家身份执行命令
     */
    COMMAND("command"),

    /**
     * 让玩家以OP身份执行命令
     */
    OP_COMMAND("op"),

    /**
     * 给玩家发送一个音效
     */
    SOUND("sound"),

    /**
     * 给全体在线挖掘发送一个音效
     */
    SOUND_ALL("soundall"),

    /**
     * 给玩家发送一个 Title + Subtitle
     */
    TITLE("title"),

    /**
     * 给全体在线玩家发送一个 Title + SubTitle
     */
    TITLE_ALL("titleall");

    private String name;

    SendType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过行获得 SendType
     * @param line 行
     * @return
     */
    public static SendType get(String line) {
        // 改为全小写
        line = line.toLowerCase();

        for (SendType value : SendType.values()) {
            if(line.startsWith(value.name+": ")) {
                return value;
            }
        }
        return MESSAGE;
    }

    public static BaseSend readSend(String line) {
        SendType type = get(line);

        switch (type) {
            case DELAY:
                return new SendDelay(line);
            case SOUND:
                return new SendSound(line);
            case SOUND_ALL:
                return new SendSoundAll(line);
            case TITLE:
                return new SendTitle(line);
            case TITLE_ALL:
                return new SendTitleAll(line);
            case CONSOLE:
                return new SendConsole(line);
            case ACTIONBAR:
                return new SendActionBar(line);
            case ACTIONBAR_ALL:
                return new SendActionBarAll(line);
            case BROADCAST:
                return new SendBroadcast(line);
            case COMMAND:
                return new SendCommand(line);
            case OP_COMMAND:
                return new SendCommandOp(line);
            default:
                return new SendMessage(line);
        }
    }

}
