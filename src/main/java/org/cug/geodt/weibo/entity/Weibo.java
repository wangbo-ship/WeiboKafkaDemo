package org.cug.geodt.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cug.geodt.weibo.util.POIUtil;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import static org.cug.geodt.weibo.util.POIUtil.parseDate;

/**
 * @className: Weibo
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/7/25 15:14
 * @version: 1.0
 */
@Data
@Entity
@Table(name = "w_weibo")
@TableName("w_weibo")
@ApiModel(value = "存放用户微博")
public class Weibo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "主键")
    private String weiboId;

    @ApiModelProperty(value = "微博正文")
    private String weiboText;

    @ApiModelProperty(value = "微博图片原始url")
    private String originalImageUrl;

    @ApiModelProperty(value = "微博图片下载后url")
    private String imageUrl;

    @ApiModelProperty(value = "微博发布位置")
    private String positionText;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "发布微博携带poi地点url")
    private String poiUrl;

    @ApiModelProperty(value = "微博发布时间")
    private String publishTime;

    @ApiModelProperty(value = "微博发布工具")
    private String publishTool;

    @ApiModelProperty(value = "发布微博用户id")
    private String userId;

    @ApiModelProperty(value = "point")
    @TableField(exist = false)
    private String geom; // WKT格式的几何数据

    @ApiModelProperty(value = "爬取微博结束时间(Date类型)")
    @TableField(exist = false)
    private Date publishTimeDate;

    public SimpleFeature toSimpleFeature(SimpleFeatureBuilder featureBuilder){
        featureBuilder.set("weibo_id", weiboId);
        featureBuilder.set("weibo_text", weiboText);
        featureBuilder.set("original_image_url", originalImageUrl);
        featureBuilder.set("image_url", imageUrl);
        featureBuilder.set("position_text", positionText);
        featureBuilder.set("lng", lng);
        featureBuilder.set("lat", lat);
        featureBuilder.set("poi_url", poiUrl);

        //将string类型时间转换为Date类型
        try {
            Date publishTimeDate = parseDate(publishTime);
            featureBuilder.set("publish_time", publishTimeDate);
        }catch (Exception e){
            e.printStackTrace();
        }

        featureBuilder.set("publish_tool", publishTool);
        featureBuilder.set("user_id", userId);

        // Assuming geom is created based on lng and lat
        if (lng != null && lat != null) {
            String geom = POIUtil.convertToWKT(lng, lat);
            featureBuilder.set("geom", geom);
        }

        return featureBuilder.buildFeature(weiboId);
    }


}
