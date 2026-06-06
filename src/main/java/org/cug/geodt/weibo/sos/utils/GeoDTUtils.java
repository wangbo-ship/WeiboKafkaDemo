package org.cug.geodt.weibo.sos.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.utils
 * @Description
 * @date 2022/12/30 16:37
 */
public class GeoDTUtils {
    public static int countString(String str, String s) {
        int count = 0,len = str.length();
        while(str.indexOf(s) != -1) {
            str = str.substring(str.indexOf(s) + 1,str.length());
            count++;
        }
        return count;
    }

    //不带括号的表达式提取器
    public static List<String> getExprFieldName(String expr){
        int fieldCount = countString(expr,"$");
        List<String> fieldNames = new ArrayList<>();
        for(int i = 0;i<fieldCount;i++){
            if(i==fieldCount-1){
                if(expr.contains(" ")){
                    fieldNames.add(expr.substring(expr.indexOf("$")+1,expr.indexOf(" ")));
                }else{
                    fieldNames.add(expr.substring(expr.lastIndexOf("$")+1));
                }

            }else {
                int startIndex = expr.indexOf("$");
                int endIndex = expr.indexOf(" ");
                int nextIndex = expr.substring(endIndex+1).indexOf("$");
                fieldNames.add(expr.substring(startIndex+1,endIndex));
                expr = expr.substring(nextIndex+endIndex+1);
            }
        }
        HashSet<String> set = new HashSet<>(fieldNames);
        return new ArrayList<>(set);
    }


}
