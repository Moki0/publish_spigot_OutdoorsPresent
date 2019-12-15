package cn.mokier.outdoorspresent.play;

import cn.mokier.outdoorspresent.play.effect.*;

public enum PlayType {

    /**
     * 延时毫秒
     */
    DELAY("delay"),

    /**
     * 播放音乐
     */
    SOUND("sound"),

    /**
     * 播放粒子
     */
    PARTICLE("particle"),

    /**
     * 播放烟花
     */
    FIREWORK("firework"),

    /**
     * 位置偏移
     */
    LOCATION("location");

    private String name;

    PlayType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过行获得 PlayType
     * @param line 行
     * @return
     */
    public static PlayType get(String line) {
        // 改为全小写
        line = line.toLowerCase();

        for (PlayType value : PlayType.values()) {
            if(line.startsWith(value.name+": ")) {
                return value;
            }
        }
        return null;
    }

    public static BasePlay readSend(String line) throws Exception {
        PlayType type = get(line);

        switch (type) {
            case DELAY:
                return new PlayDelay(line);
            case SOUND:
                return new PlaySound(line);
            case PARTICLE:
                return new PlayParticle(line);
            case FIREWORK:
                return new PlayFirework(line);
            case LOCATION:
                return new PlayLocation(line);
            default:
                throw new Exception("错误格式，行 " + line + "没有添加有效的类型");
        }
    }

}
