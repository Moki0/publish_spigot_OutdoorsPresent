package cn.mokier.outdoorspresent.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MsgUtils {

    private final static String DIGIT = "\\d+";
    private final static String NON_DIGIT = "\\D+";

    public static Integer getRandomNumber(String msg) {
        Integer min = Integer.parseInt(msg.split("-")[0]);
        Integer max = Integer.parseInt(msg.split("-")[1]);
        return (int)(min+Math.random()*(max-min+1));
    }

    /**
     * 判断一个字符串是否都为数字
     * @param msg  字符串
     * @return
     */
    public static boolean isDigit(String msg) {
        Pattern pattern = Pattern.compile(DIGIT);
        Matcher matcher = pattern.matcher(msg);
        return matcher.matches();
    }

    /**
     * 截取字符串中的数字
     * @param msg  字符串
     * @return
     */
    public static Integer getNumbers(String msg) {
        Pattern pattern = Pattern.compile(DIGIT);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }
        return null;
    }

    /**
     * 截取字符串中的非数字
     * @param msg  字符串
     * @return
     */
    public static String getNotNumber(String msg) {
        Pattern pattern = Pattern.compile(NON_DIGIT);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 判断字符串是否存在过滤信息
     * @param msg 字符串
     * @param filters 过滤信息
     * @return
     */
    public static boolean contains(String msg, List<String> filters) {
        for(String filter : filters) {
            if(msg.contains(filter)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 过滤器
     * @param str 要过滤的字符串
     * @param values  过滤的信息
     * @return
     */
    public static String filterVar(String str, String... values) {
        str = ChatColor.translateAlternateColorCodes('&', str);
        if(values != null) {
            if(values.length % 2 == 0) {
                for(int i = 0;i < values.length;) {
                    str = str.replace(values[i], values[i + 1]);
                    i += 2;
                    if(i >= values.length) {
                        break;
                    }
                }
            }
        }
        return str;
    }

    /**
     * 过滤器
     * @param strs 要过滤的字符串集合
     * @param values 过滤的信息
     * @return
     */
    public static List<String> filterVar(List<String> strs, String... values) {
        List<String> list = new ArrayList<>();
        for(String str : strs) {
            list.add(filterVar(str, values));
        }
        return list;
    }


    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString  原始字符串
     * @param length  指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }

        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str   原始字符串
     * @param f  开始位置
     * @param t  结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length()) {
            return null;
        }
        if (t > str.length()) {
            return str.substring(f);
        } else {
            return str.substring(f, t);
        }
    }

    /**
     * 计算概率
     * @param msg
     * @return
     */
    public static boolean odds(String msg) {
        Integer odds = Integer.parseInt(msg.split("/")[0]);
        Integer maxOdds = Integer.parseInt(msg.split("/")[1]);

        Integer ran = (int)(0+Math.random()*(maxOdds-0+1));

        return ran <= odds;
    }

}
