package org.cug.geodt.weibo.kafka.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.cug.geodt.weibo.entity.User;
import org.cug.geodt.weibo.entity.UserEntity;
import org.cug.geodt.weibo.entity.Weibo;
import org.cug.geodt.weibo.kafka.constant.Topic;
import org.cug.geodt.weibo.mapper.UserEntityMapper;
import org.cug.geodt.weibo.mapper.UserMapper;
import org.cug.geodt.weibo.mapper.WeiboMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @className: WeiboConsumer
 * @author: caiyixun
 * @description: 微博id数据消费者
 * @date: 2024/5/14 11:22
 * @version: 1.0
 */
@Slf4j
@Component
public class WeiboConsumer {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private WeiboMapper weiboMapper;

//    @Async
//    @KafkaListener(groupId = "test_id", topics = Topic.SIMPLE_ID, containerFactory = "kafkaListenerContainerFactory")
//    public void listenID(ConsumerRecord<String, String> record) {
//        log.info("开始处理poi下用户id消息：{}", record.value());
//        processIdMessage(record.value());
//    }

//    @Async
//    @KafkaListener(groupId = "WEIBO_POI", topics = Topic.WEIBO_POI, containerFactory = "kafkaListenerContainerFactory")
//    public void listenAllID(ConsumerRecord<String, String> record) {
//        log.info("开始处理poi下所有签到微博消息：{}", record.value());
//        processAllIdMessage(record.value());
//    }
//
//    @Async
//    @KafkaListener(groupId = "USER_END_DATE", topics = Topic.USER_END_DATE, containerFactory = "kafkaListenerContainerFactory")
//    public void listenDate(ConsumerRecord<String, String> record) {
//        log.info("开始处理用户微博爬取结束date消息：{}", record.value());
//        processDateMessage(record.value());
//    }
//
//    @Async
//    @KafkaListener(groupId = "USER_WEIBO", topics = Topic.USER_WEIBO, containerFactory = "kafkaListenerContainerFactory")
//    public void listenWeibo(ConsumerRecord<String, String> record) {
//        log.info("开始用户主页处理weibo消息：{}", record.value());
//        processWeiboMessage(record.value());
//    }

    public void processAllIdMessage(String message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            List<BigInteger> userIds = new ArrayList<>();
            List<String> userNames = new ArrayList<>();
            List<String> createdAts = new ArrayList<>();
            List<String> weiboTexts = new ArrayList<>();

            // 读取 user_id 数组
            JsonNode userIdNode = jsonNode.get("user_id");
            if (userIdNode != null && userIdNode.isArray()) {
                for (JsonNode id : userIdNode) {
                    // 将字符串转换为 BigInteger
                    BigInteger bigInteger = new BigInteger(id.asText());
                    userIds.add(bigInteger);
                }
            }

            // 读取 user_name 数组
            JsonNode userNameNode = jsonNode.get("user_name");
            if (userNameNode != null && userNameNode.isArray()) {
                for (JsonNode name : userNameNode) {
                    userNames.add(name.asText());
                }
            }

            // 读取 created_at 数组
            JsonNode createdAtNode = jsonNode.get("created_at");
            if (createdAtNode != null && createdAtNode.isArray()) {
                for (JsonNode createdAt : createdAtNode) {
                    createdAts.add(createdAt.asText());
                }
            }

            // 读取 weibo_text 数组
            JsonNode weiboTextNote = jsonNode.get("weibo_text");
            if (weiboTextNote != null && weiboTextNote.isArray()) {
                for (JsonNode weiboText : weiboTextNote) {
                    weiboTexts.add(weiboText.asText());
                }
            }

            // 读取 poi_id
            String poiId = jsonNode.get("poi_id").asText();

            UserEntity user = new UserEntity();

            for (int i = 0; i < userIds.size(); i++) {
                BigInteger userId = userIds.get(i);
                String userName = userNames.get(i);
                String createdAt = createdAts.get(i);
                String weiboText = weiboTexts.get(i);
                user.setId(UUID.randomUUID().toString());
                user.setPoiId(poiId);
                user.setUserId(userId);
                user.setUserName(userName);
                user.setCreatedAt(createdAt);
                user.setWeiboText(weiboText);

                userEntityMapper.insert(user);
            }

        } catch (DataIntegrityViolationException e) {
            log.error("处理id消息时发生数据完整性冲突异常", e);
        } catch (Exception e) {
            log.error("处理id消息时发生异常", e);
        }
    }

    public void processIdMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            List<BigInteger> userIds = new ArrayList<>();
            List<String> userNames = new ArrayList<>();

            // 读取 user_id 数组
            JsonNode userIdNode = jsonNode.get("user_id");
            if (userIdNode != null && userIdNode.isArray()) {
                for (JsonNode id : userIdNode) {
                    // 将字符串转换为 BigInteger
                    BigInteger bigInteger = new BigInteger(id.asText());
                    userIds.add(bigInteger);
                }
            }

            // 读取 user_name 数组
            JsonNode userNameNode = jsonNode.get("user_name");
            if (userNameNode != null && userNameNode.isArray()) {
                for (JsonNode name : userNameNode) {
                    userNames.add(name.asText());
                }
            }

            User user = new User();

            for (int i = 0; i < userIds.size(); i++) {
                BigInteger userId = userIds.get(i);
                String userName = userNames.get(i);
                user.setUserId(userId);
                user.setUserName(userName);
                userMapper.insert(user);
            }

        } catch (DataIntegrityViolationException e) {
            log.error("处理id消息时发生数据完整性冲突异常", e);
        } catch (Exception e) {
            log.error("处理id消息时发生异常", e);
        }
    }

    public void processDateMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            String userIdNode = jsonNode.get("user_id").asText();
            String endDate = jsonNode.get("end_date").asText();

            BigInteger userId = new BigInteger(userIdNode);

            User user = new User();

            user.setUserId(userId);
            user.setEndDate(endDate);

            int i = userMapper.updateById(user);
            if(i == 1){
                log.info("更新成功");
            }

        } catch (Exception e) {
            log.error("处理date消息时发生异常", e);
        }
    }

    public void processWeiboMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            // 逐个读取每个字段的值
            String id = jsonNode.get("id").asText();
            String userId = jsonNode.get("user_id").asText();
            String content = jsonNode.get("content").asText();
            String originalPictures = jsonNode.get("original_pictures").asText();
            String poiUrl = jsonNode.get("poi_url").asText();
            JsonNode filePathListNode = jsonNode.get("file_path_list");
            double lng = jsonNode.get("lng").asDouble();
            double lat = jsonNode.get("lat").asDouble();
            String publishPlace = jsonNode.get("publish_place").asText();
            String publishTime = jsonNode.get("publish_time").asText();
            String publishTool = jsonNode.get("publish_tool").asText();

//            String articleUrl = jsonNode.get("article_url").asText();
//            JsonNode retweetPictures = jsonNode.get("retweet_pictures");
//            boolean original = jsonNode.get("original").asBoolean();
//            String videoUrl = jsonNode.get("video_url").asText();
//            int upNum = jsonNode.get("up_num").asInt();
//            int retweetNum = jsonNode.get("retweet_num").asInt();
//            int commentNum = jsonNode.get("comment_num").asInt();

            String filePathList = "";
            if (filePathListNode.isArray()) {
                StringBuilder filePathBuilder = new StringBuilder();

                // 遍历数组并将每个文件路径追加到 StringBuilder 中
                for (JsonNode filePathNode : filePathListNode) {
                    if (filePathBuilder.length() > 0) {
                        filePathBuilder.append(",");  // 使用逗号分隔
                    }
                    filePathBuilder.append(filePathNode.asText());
                }

                // 转换为字符串并存储
                filePathList = filePathBuilder.toString();
            }

            Weibo weibo = new Weibo();
            weibo.setWeiboId(id);
            weibo.setWeiboText(content);
            weibo.setOriginalImageUrl(originalPictures);
            weibo.setImageUrl(filePathList);
            weibo.setPositionText(publishPlace);
            weibo.setLng(lng);
            weibo.setLat(lat);
            weibo.setPoiUrl(poiUrl);
            weibo.setPublishTime(publishTime);
            weibo.setPublishTool(publishTool);
            weibo.setUserId(userId);
            weiboMapper.insert(weibo);

        } catch (Exception e) {
            log.error("处理weibo消息时发生异常", e);
        }
    }
}

