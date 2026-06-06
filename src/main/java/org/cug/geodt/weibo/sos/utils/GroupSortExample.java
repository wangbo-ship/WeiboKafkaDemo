package org.cug.geodt.weibo.sos.utils;

/**
 * @ClassName : GroupSortExample  //类名
 * @Description : 排序分组类  //描述
 * @Author : cyx //作者
 * @Date: 2023/8/12  16:28
 */
public class GroupSortExample {

    // 提取字符串中的类型
    public static String extractType(String str) {
        String[] parts = str.split(":");
        if (parts.length >= 3) {
            return parts[1];
        }
        return "";
    }

    // 提取字符串中的数字部分
    public static int extractNumber(String str) {
        String[] parts = str.split(":");
        if (parts.length >= 3) {
            return Integer.parseInt(parts[2]);
        }
        return 0;
    }

}
