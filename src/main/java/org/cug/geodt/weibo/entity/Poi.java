package org.cug.geodt.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @className: PoiEntity
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/7/10 16:50
 * @version: 1.0
 */
@Data
@Entity
@Table(name = "w_poi")
@TableName("w_poi")
@ApiModel(value = "poi基本信息")
public class Poi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "poi id")
    private String poiId;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "poi名称")
    private String poiName;

    @ApiModelProperty(value = "大类")
    private String largeCategory;

    @ApiModelProperty(value = "中类")
    private String mediumCategory;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "微博总数")
    private String weiboNum;

    @ApiModelProperty(value = "爬取微博结束时间")
    private String endTime;

    @ApiModelProperty(value = "point")
    @TableField(exist = false)
    private String geom; // WKT格式的几何数据

    @ApiModelProperty(value = "爬取微博结束时间(Date类型)")
    @TableField(exist = false)
    private Date endTimeDate;

    public SimpleFeature toSimpleFeature(SimpleFeatureBuilder featureBuilder) {
        featureBuilder.set("poi_id", poiId);
        featureBuilder.set("city_code", cityCode);
        featureBuilder.set("poi_name", poiName);
        featureBuilder.set("large_category", largeCategory);
        featureBuilder.set("medium_category", mediumCategory);
        featureBuilder.set("lng", lng);
        featureBuilder.set("lat", lat);
        featureBuilder.set("province", province);
        featureBuilder.set("city", city);
        featureBuilder.set("area", area);
        featureBuilder.set("weibo_num", weiboNum);
        featureBuilder.set("end_time", endTimeDate);
        featureBuilder.set("geom", geom);

//        String uniqueId = UUID.randomUUID().toString();
//        return featureBuilder.buildFeature(uniqueId);

        return featureBuilder.buildFeature(poiId);
    }

    @Override
    public String toString() {
        return "Poi{" +
                "poiId='" + poiId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", poiName='" + poiName + '\'' +
                ", largeCategory='" + largeCategory + '\'' +
                ", mediumCategory='" + mediumCategory + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", weiboNum='" + weiboNum + '\'' +
                ", endTime='" + endTime + '\'' +
                ", geom='" + geom + '\'' +
                ", endTimeDate=" + endTimeDate +
                '}';
    }
}
