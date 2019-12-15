package cn.mokier.outdoorspresent.chat;

import io.izzel.taboolib.module.config.TConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lang {

    private static TConfig config;
    private static Map<String, List<String>> messageMap;

    public static void load(TConfig tConfig) {
        config = tConfig;

        initMessageMap();
    }

    public static List<String> getMessage(String node) {
        return messageMap.get(node);
    }

    /**
     * 初始化信息集合
     */
    private static void initMessageMap() {
        messageMap = new HashMap<>();
        for(String node : config.getKeys(true)) {
            List<String> msgList = getMessageByNode(node);

            if(msgList!=null) {
                messageMap.put(node, msgList);
            }
        }
    }

    /**
     * 通过路径获得信息
     * @param node 路径
     * @return
     */
    private static List<String> getMessageByNode(String node) {
        if(config.isConfigurationSection(node) || config.getString(node) == null) {
            return null;
        }

        List<String> msgList = new ArrayList<>();
        if(config.isList(node)) {
            msgList.addAll(config.getStringList(node));
        }else {
            msgList.add(config.getString(node));
        }

        return msgList;
    }
}