package org.cug.geodt.weibo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.cug.geodt.weibo.mapper.PoiMapper;
import org.cug.geodt.weibo.mapper.UserEntityMapper;
import org.cug.geodt.weibo.util.ExcelReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeiboDataConsumerApplicationTests {
    public static String parentDirectoryPath = "C:\\Users\\cyx\\Desktop\\fsdownload";

    public static List<String> list = new ArrayList<>();

    @Autowired
    private ExcelReader excelReader;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Test
    public void contextLoads() {
        excelReader.init();
    }

    @Test
    public void test() throws JsonProcessingException {
        String a = "{\"user_id\": \"5878385191\", \"user_name\": \"烦恼开心快乐多\", \"end_date\": \"2024-05-20 15:15\"}";
        String b = "\"{\\\"user_id\\\": \\\"5878385191\\\", \\\"user_name\\\": \\\"烦恼开心快乐多\\\", \\\"end_date\\\": \\\"2024-05-20 15:15\\\"}\"";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(b);

        String userIdNode = jsonNode.get("user_id").asText();
        String userName = jsonNode.get("user_name").asText();
        String endDate = jsonNode.get("end_date").asText();
        System.out.println(userIdNode);
        System.out.println(userName);
        System.out.println(endDate);
    }

    @Test
    public void test01(){

    }


}
