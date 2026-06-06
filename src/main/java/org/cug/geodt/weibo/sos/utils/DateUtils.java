package org.cug.geodt.weibo.sos.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.utils
 * @Description
 * @date 2022/12/28 17:33
 */
public class DateUtils {
    //Long转String时间
    public static String longToDateString(Long timestamp){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        return format.format(timestamp);
    }
    //获取当天开始时间时间戳
    public static Long getStartOfDay19Formatter2Long(String date){
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return startOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long getStartOfMonthLong2Long(Long timestamp){
        LocalDate localDate= LocalDate.parse(longToDateString(timestamp), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime startOfMonth = localDate.withDayOfMonth(1).atStartOfDay();
        return startOfMonth.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long getEndOfMonthLong2Long(Long timestamp){
        LocalDate localDate= LocalDate.parse(longToDateString(timestamp), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).atStartOfDay().minusSeconds(1);
        return lastDayOfMonth.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long getStartOfYearLong2Long(Long timestamp){
        LocalDate localDate= LocalDate.parse(longToDateString(timestamp), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime startDayOfMonth = localDate.withDayOfYear(1).atStartOfDay();
        return startDayOfMonth.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long getEndOfYearLong2Long(Long timestamp){
        LocalDate localDate= LocalDate.parse(longToDateString(timestamp), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfYear()).plusDays(1).atStartOfDay().minusSeconds(1);
        return lastDayOfMonth.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long getEndOfDayLong2Long(Long timestamp){
        LocalDate localDate= LocalDate.parse(longToDateString(timestamp), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime lastDayOfDay = localDate.plusDays(1).atStartOfDay().minusSeconds(1);
        return lastDayOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }


    public static Long getStartOfDayLong2Long(Long timestamp){
        LocalDate localDate= LocalDate.parse(longToDateString(timestamp), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime startDayOfDay = localDate.atStartOfDay();
        return startDayOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }


    public static Long dateToLong(String date){
        LocalDateTime localDateTime= LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;
    }

    //获取当天结束时间时间戳
    public static Long getEndOfDay19Format2Long(String date){
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay().minusSeconds(1);
        return endOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }



    public static Long getEndOfDay10Format2Long(String date) {
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay().minusSeconds(1);
        return endOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long getStartOfDay10Format2Long(String date) {
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return startOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static String getStartOfDay10Format2String(String date) {
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime localDateTime = localDate.atStartOfDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static String getEndOfDay10Format2String(String date) {
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return endOfDay.format(formatter);
    }

    public static String localDataTime2String(LocalDateTime dateStart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateStart.format(formatter);
    }

    public static String Date2String(Date dateStart) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(dateStart);
    }

    public static String convertToISO8601(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        return formatter.format(instant);
    }

    public static Long ISO8601convertToLong(String date) {
        String time = date.replaceAll(" ","");
        Instant instant = Instant.parse(time);
        long epochSecond = instant.getEpochSecond();
        return epochSecond;
    }


    private static int calculateCount(Integer startTime,Integer endTime,String aggSpan, int aggSpanTime) {
        if (aggSpan.equals("quarter")){
            int i = (endTime-startTime) / (15*60);
            System.out.println(i);
            return 0;
        }else if(aggSpan.equals("hour")) {

            return 0;
        } else if (aggSpan.equals("day")){

            return 0;
        }else if (aggSpan.equals("week")){

            return 0;
        }else if (aggSpan.equals("month")){

            return 0;
        }else if (aggSpan.equals("year")){

            return 0;
        }else {
            return 0;
        }
    }




    public static void main(String[] args) {
        calculateCount(1692374400,1692375240,"quarter",15*60);
    }

}
