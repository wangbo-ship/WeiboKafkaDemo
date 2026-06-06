package org.cug.geodt.weibo.sos.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @FileName TranslationUtils
 * @Author WJW
 * @Date 2023/7/28 10:11
 * @Description 英文翻译为中文
 */
public class TranslationUtils {

    public static String ToChinese (String english) {
        // 设置区域为中文
        Locale locale = new Locale("zh", "CN");

        // 加载属性文件中的翻译内容
        ResourceBundle bundle = ResourceBundle.getBundle("messages_zh_CN",locale);
        String Chinese = bundle.getString(english);
        return Chinese;
    }



    public static void main(String [] args) {
        String hello = ToChinese("hello");
        System.out.println(hello);

    }


}
