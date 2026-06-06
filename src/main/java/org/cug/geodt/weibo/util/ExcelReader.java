package org.cug.geodt.weibo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.cug.geodt.weibo.kafka.constant.Topic;
import org.cug.geodt.weibo.kafka.producer.WeiboIdProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: ExcelReader
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/5/15 15:58
 * @version: 1.0
 */
@Component
public class ExcelReader {

    public static String parentDirectoryPath = "C:\\Users\\cyx\\Desktop\\fsdownload";

    public static List<String> list = new ArrayList<>();

    @Autowired
    private WeiboIdProducer weiboIdProducer;

//    @PostConstruct
    public void init() {
        getAllFilePath();
        for (String filePath : list) {
            List<String> userId = new ArrayList<>();
            List<String> userName = new ArrayList<>();

            // 调用方法读取数据
            readExcelData(filePath, userId, userName);
        }
    }

    private void readExcelData(String csvFile, List<String> userId, List<String> userName) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                if (tokenizer.hasMoreTokens()) {
                    userId.add(tokenizer.nextToken());
                    if (tokenizer.hasMoreTokens()) {
                        userName.add(tokenizer.nextToken());
                    }
//                    else {
//                        // 如果第二列为空，则添加空字符串或其他默认值
//                        userName.add("");
//                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 处理文件读取错误
        }
        // 构建包含 user_id 和 user_name 的 Map 对象
        Map<String, List<String>> data = new HashMap<>();
        if(userId.size() != 0 && userName.size() != 0){
            data.put("user_id", userId);
            data.put("user_name", userName);

            System.out.println(csvFile);
            // 输出读取的数据
//            System.out.println("userId: " + userId);
//            System.out.println("userName: " + userName);
//            System.out.println("-------------------------");

            // 将 Map 对象转换为 JSON 字符串
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = mapper.writeValueAsString(data);
                sendMessage(jsonString);
            } catch (Exception e) {
//            e.printStackTrace();
            }
        }

    }

    public void getAllFilePath() {

        // 获取父目录的File对象
        File parentDirectory = new File(parentDirectoryPath);

        ArrayList<String> list = new ArrayList<>();

        // 检查父目录是否存在
        if (parentDirectory.exists() && parentDirectory.isDirectory()) {
            // 调用递归方法获取所有文件和文件夹名字
            listFiles(parentDirectory, "");
        }
    }

    private void listFiles(File directory, String currentPath) {
        // 获取目录中的文件列表
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 输出文件夹名称
//                    System.out.println(currentPath + file.getName() + "/");
                    // 递归调用处理子目录
                    listFiles(file, currentPath + file.getName() + "/");
                } else {
                    // 输出文件名称
                    list.add(parentDirectoryPath + "/" + currentPath + file.getName());
                }
            }
        }
    }

    public void sendMessage(String jsonString){
        weiboIdProducer.sendMessage(Topic.USER_WEIBO, jsonString);
    }

}
