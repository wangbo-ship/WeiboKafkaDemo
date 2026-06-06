package org.cug.geodt.weibo.sos.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;

/**
 * @author developer
 */
public class ConsoleUtils {

    public static void info(String content) {
        System.out.println(LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_MS_PATTERN) + "  INFO: " + content);
    }

    public static void error(String content) {
        System.err.println(LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_MS_PATTERN) + "  ERROR: " + content);
    }

}
