package org.cug.geodt.weibo.sos.utils;

import org.cug.geodt.weibo.sos.config.SnowflakeIdWorkerConfig;
import org.geotools.filter.v2_0.FESConfiguration;
import org.geotools.xsd.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Author WJW
 * Date 2023/5/6 10:16
 */
public class StringUtils {
    /**
     *清除入参全角空格及转义字符，及双引号
     * @author WJW
     */
    public static String cleanBlank(String str){
        if (str != null && (str.contains("\\") || str.contains("　") || str.contains("\""))) {
            String replaceFlag = str.replace("\\", "");
            str = replaceFlag.replace("\"", "").replaceAll("　", "").trim();
            return str;
        }
        return str;
    }

    /**
     *清除入参全角空格及转义字符，及双引号
     * @author WJW
     */
    public static String cleanEsc(String str){
        if (str != null && str.contains("\\")) {
            str = str.replace("\\", "").trim();
            return str;
        }
        return str;
    }

    /**
     *
     */
    public static String analysisHttpRequest(String str){
        if (str != null && str.contains("//")){
            try {
                String[] split = str.split("/");
//            int length = split.length;
                return split[split.length -1];
            }catch (Exception ArrayIndexOutOfBoundsException){
                return str;
            }
        }
        return str;
    }

    public static HashMap<String, ?> XmlToHashMap(String xml) throws IOException, ParserConfigurationException, SAXException {
        FESConfiguration configuration = new FESConfiguration();
        Parser parser = new Parser(configuration);
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        HashMap<String, ?> map = (HashMap<String, ?>) parser.parse(inputStream);
        return map;
    }

    /**
     * 解析数据: 解析result 标签下的value数据
     * @param value
     * @return
     */
    public static ArrayList<ArrayList<String>> valuesParser(String value,String split1, String split2) {
        ArrayList<ArrayList<String>> valuesArrayList = new ArrayList<>();
        String[] Records = value.split(split1);
        for (String record : Records) {
            String[] metaValue = record.split(split2,-1);
            ArrayList<String> collect = Arrays.stream(metaValue).collect(Collectors.toCollection(ArrayList::new));
            collect.set(0,DateUtils.ISO8601convertToLong(collect.get(0)).toString());
            valuesArrayList.add(collect);
        }
        return valuesArrayList;
    }

    public static String SensorIdGenerator(String sensorType) {
        SnowflakeIdWorkerConfig idWorker = new SnowflakeIdWorkerConfig(0, 0);
        long id = idWorker.nextId();
        return "urn:" + sensorType + ":" + id;
    }

    public static String SensorIdParseToSensorType(String sensorId) {
        String[] split = sensorId.split(":");
        return !(split[2] == null) && !(split[1] == null)? split[1]+":"+split[2] : null;
    }

    public static String catYear(String date) {

        String[] parts = date.split("-");

        if (parts[1].startsWith("0"))
            parts[1] = parts[1].replace("0","");
        //判断是否有天
        if (parts.length == 3) {
            if (parts[2].startsWith("0"))
                parts[2] = parts[2].replace("0","");
            return parts[1] + "-" + parts[2];
        }
        return  parts[1];

    }

    public static void main(String [] args) {
        String s = catYear("2023-12-10");
        System.out.println(s);

    }


}
